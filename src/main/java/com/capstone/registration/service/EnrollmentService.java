package com.capstone.registration.service;

import com.capstone.registration.Course;
import com.capstone.registration.Enrollment;
import com.capstone.registration.Student;
import com.capstone.registration.Waiting;
import com.capstone.registration.dto.EnrollmentRequestDto;
import com.capstone.registration.repository.CourseRepository;
import com.capstone.registration.repository.EnrollmentRepository;
import com.capstone.registration.repository.StudentRepository;
import com.capstone.registration.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final WaitingRepository waitingRepository;

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
            enrollmentRepository.save(enrollment);
            return "SUCCESS";

        } catch (IllegalStateException e) {
            // 4. 정원이 꽉 찼거나 동시성 충돌로 밀려났다면 대기열(Waiting)로 등록
            Waiting waiting = Waiting.builder()
                    .student(student)
                    .course(course)
                    .build();
            waitingRepository.save(waiting);
            return "WAITING";
        }
    }
}