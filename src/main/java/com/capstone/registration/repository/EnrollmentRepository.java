package com.capstone.registration.repository;

import java.util.List;
import com.capstone.registration.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // 학번, 과목ID로 수강 확정 내역 찾기
    Optional<Enrollment> findByStudent_StudentIdAndCourse_Id(String studentId, Long courseId);
    // 특정 학생의 '수강 확정' 내역 모두 찾기
    List<Enrollment> findByStudent_StudentId(String studentId);
}