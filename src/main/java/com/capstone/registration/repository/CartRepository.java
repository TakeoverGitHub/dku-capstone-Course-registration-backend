package com.capstone.registration.repository;

import com.capstone.registration.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // 특정 학생의 장바구니 내역 전체 조회
    List<Cart> findByStudent_StudentId(String studentId);

    // 이미 담았는지 확인용
    boolean existsByStudent_StudentIdAndCourse_Id(String studentId, Long courseId);
}