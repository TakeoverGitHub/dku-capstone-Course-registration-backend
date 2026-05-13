package com.capstone.registration.component;

import com.capstone.registration.Course;
import com.capstone.registration.repository.CourseRepository;
import com.capstone.registration.Student;
import com.capstone.registration.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
// 테스트용 더미 데이터 주입 코드입니다.
public class DummyDataLoader implements CommandLineRunner {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {

        // 1. 테스트용 학생 데이터 주입 (DB에 없을 때만)
        if (studentRepository.count() == 0) {
            Student testStudent = Student.builder()
                    .studentId("32201234") // 테스트용 학번 (나중에 API 쏠 때 이 번호 쓸 거임)
                    .password("1234")
                    .name("박팀장")
                    .maxCredit(21)
                    .build();
            studentRepository.save(testStudent);
            System.out.println("✅ 테스트용 학생 데이터 주입 완료!");
        }

        // DB에 강의가 하나도 없을 때만 더미 데이터를 넣음 (중복 방지)
        // 2. 테스트용 강의 데이터 주입
        if (courseRepository.count() == 0) {
            Course course1 = Course.builder()
                    .courseCode("CS101")
                    .classNo(1)
                    .courseName("자바 프로그래밍")
                    .credit(3)
                    .maxCapacity(40)
                    .dayOfWeek("월")
                    .startTime(1)
                    .endTime(3)
                    .wish(1)
                    .remain(0)
                    .campus("죽전")
                    .major("SW융합 소프트웨어학과")
                    .category("전공필수")
                    .courseType("전공")
                    .grade(2)
                    .build();

            Course course2 = Course.builder()
                    .courseCode("SWE202")
                    .classNo(1)
                    .courseName("소프트웨어 공학")
                    .credit(3)
                    .maxCapacity(30)
                    .dayOfWeek("화")
                    .startTime(2)
                    .endTime(4)
                    .wish(0)
                    .remain(0)
                    .campus("죽전")
                    .major("SW융합 소프트웨어학과")
                    .category("전공선택")
                    .courseType("전공")
                    .grade(3)
                    .build();

            Course course3 = Course.builder()
                    .courseCode("DB303")
                    .classNo(2)
                    .courseName("데이터베이스 개론")
                    .credit(3)
                    .maxCapacity(50)
                    .dayOfWeek("수")
                    .startTime(5)
                    .endTime(7)
                    .wish(0)
                    .remain(0)
                    .campus("죽전")
                    .major("SW융합 소프트웨어학과")
                    .category("전공필수")
                    .courseType("전공")
                    .grade(2)
                    .build();

            courseRepository.saveAll(List.of(course1, course2, course3));
            System.out.println("더미 데이터 주입 완료");
        }
    }
}