package com.capstone.registration.service;

import com.capstone.registration.Course;
import com.capstone.registration.Enrollment;
import com.capstone.registration.Student;
import com.capstone.registration.Waiting;
import com.capstone.registration.dto.CourseResponseDto;
import com.capstone.registration.dto.EnrollmentRequestDto;
import com.capstone.registration.repository.CourseRepository;
import com.capstone.registration.repository.EnrollmentRepository;
import com.capstone.registration.repository.StudentRepository;
import com.capstone.registration.repository.WaitingRepository;
import com.capstone.registration.WaitingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final WaitingRepository waitingRepository;
    private final EmailService emailService;

    @Transactional
    public String enroll(EnrollmentRequestDto requestDto) {
        // 1. 창고(DB)에서 학생과 강의 데이터를 꺼내옴
        Student student = studentRepository.findById(requestDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));
        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));

        // 2. 학생 학점 차감 (대기열에 가든 수강 성공하든 학점은 감소)
        student.deductCredit(course.getCredit());

        try {
            // 3. 수강 인원 증가 시도 (여기서 @Version 낙관적 잠금 작동)
            course.increaseEnrollment();

            // 정원 내에 들어왔다면 수강 확정(Enrollment) 저장
            Enrollment enrollment = Enrollment.builder()
                    .student(student)
                    .course(course)
                    .build();

            //save 대신 saveAndFlush를 사용하여 즉시 DB 반영 및 충돌 테스트
            enrollmentRepository.saveAndFlush(enrollment);
            return "SUCCESS";

        } catch (IllegalStateException | OptimisticLockingFailureException e) {
            // 4. 정원이 꽉 찼거나 동시성 충돌로 밀려났다면 대기열(Waiting)로 등록
            Waiting waiting = Waiting.builder()
                    .student(student)
                    .course(course)
                    .build();
            waitingRepository.save(waiting);
            return "WAITING";
        }
    }

    @Transactional
    public String cancelEnrollment(EnrollmentRequestDto requestDto) {
        // 1. 수강 내역 조회 (없으면 에러)
        Enrollment enrollment = enrollmentRepository.findByStudent_StudentIdAndCourse_Id(
                requestDto.getStudentId(), requestDto.getCourseId()
        ).orElseThrow(() -> new IllegalArgumentException("해당 과목의 수강 내역이 존재하지 않습니다."));

        Student student = enrollment.getStudent();
        Course course = enrollment.getCourse();

        // 2. 수강 인원 감소 및 학생 학점 반환
        course.decreaseEnrollment();
        student.refundCredit(course.getCredit());

        // 3. 수강 확정 내역 삭제 (빈자리 발생)
        enrollmentRepository.delete(enrollment);

        // 스마트 스왑 호출
        processSmartSwap(course);

        return "CANCEL_SUCCESS";
    }

    private void processSmartSwap(Course course) {
        // 1. 빈자리가 생긴 과목의 대기열 1번 찾기
        waitingRepository.findFirstByCourse_IdAndStatusOrderByCreatedAtAsc(course.getId(), WaitingStatus.WAITING)
                .ifPresent(waiting -> {
                    // 2. 대기 1번이 존재한다면, 상태를 수강 확정(TRANSFERRED)으로 변경
                    waiting.transferToEnrollment();

                    // 3. 실제 수강 확정 내역(Enrollment) 생성
                    Enrollment newEnrollment = Enrollment.builder()
                            .student(waiting.getStudent())
                            .course(course)
                            .build();
                    enrollmentRepository.save(newEnrollment);

                    // 4. 수강 인원 다시 1명 증가 (취소로 1명 줄었던 걸 대기자가 채움)
                    course.increaseEnrollment();

                    // 이메일 유무 검사 로직
                    String targetEmail = waiting.getStudent().getEmail();

                    // 이메일 칸이 null이 아니고, 빈칸도 아닐 때만 발송
                    if (targetEmail != null && !targetEmail.trim().isEmpty()) {
                        emailService.sendEnrollmentSuccessEmail(
                                targetEmail,
                                waiting.getStudent().getName(),
                                course.getCourseName()
                        );
                        System.out.println("✅ 이메일 발송 성공: " + targetEmail); // 로그 확인용
                    } else {
                        // 이메일이 없는 더미 데이터는 조용히 스킵 (서버 에러 방지)
                        System.out.println("⚠️ 이메일 정보 없음 - 발송 스킵 (학번: " + waiting.getStudent().getStudentId() + ")");
                    }
                });
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getMyPage(String studentId) {
        // 1. 학생 기본 정보 (남은 학점 등) 조회
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));

        // 2. 수강 확정된 강의 목록 조회 & DTO로 변환
        List<CourseResponseDto> enrolledCourses = enrollmentRepository.findByStudent_StudentId(studentId)
                .stream()
                .map(enrollment -> CourseResponseDto.from(enrollment.getCourse()))
                .toList();

        // 3. 현재 대기 중인 강의 목록 조회 & DTO로 변환
        List<CourseResponseDto> waitingCourses = waitingRepository.findByStudent_StudentIdAndStatus(studentId, WaitingStatus.WAITING)
                .stream()
                .map(waiting -> CourseResponseDto.from(waiting.getCourse()))
                .toList();

        // 4. 맵(Map)으로 묶어서 프론트로 반환
        return Map.of(
                "studentName", student.getName(),
                "availableCredit", student.getAvailableCredit(),
                "enrolledCourses", enrolledCourses,
                "waitingCourses", waitingCourses
        );
    }
}