package com.capstone.registration.service;

import com.capstone.registration.*;
import com.capstone.registration.dto.CourseResponseDto;
import com.capstone.registration.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // 장바구니에 원하는 과목을 추가
    public String addToCart(String studentId, Long courseId) {
        if (cartRepository.existsByStudent_StudentIdAndCourse_Id(studentId, courseId)) {
            throw new IllegalArgumentException("이미 장바구니에 담긴 과목입니다.");
        }

        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();

        cartRepository.save(Cart.builder().student(student).course(course).build());
        return "SUCCESS";
    }

    // 장바구니에 담아놓은 과목들 조회
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getMyCart(String studentId) {
        return cartRepository.findByStudent_StudentId(studentId).stream()
                .map(cart -> CourseResponseDto.from(cart.getCourse()))
                .collect(Collectors.toList());
    }
}