package com.capstone.registration.controller;

import com.capstone.registration.Student;
import com.capstone.registration.dto.EnrollmentRequestDto;
import com.capstone.registration.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EnrollmentRequestDto loginDto) {
        // 1. 서비스에 인증 위임
        Student student = authService.authenticate(loginDto.getStudentId(), loginDto.getPassword());

        // 2. 성공 시 학생 전체 정보(studentId, name 등)를 반환
        return ResponseEntity.ok(student);
    }
}