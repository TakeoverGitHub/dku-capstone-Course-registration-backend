package com.capstone.registration.controller;

import com.capstone.registration.Student;
import com.capstone.registration.service.AuthService; // AuthService 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        // 1. Map에서 프론트가 보낸 학번과 비밀번호 뽑아냄
        String studentId = loginData.get("studentId");
        String password = loginData.get("password");

        // 2. 서비스에 인증 위임
        Student student = authService.authenticate(studentId, password);

        // 3. 성공 시 학생 정보 반환
        return ResponseEntity.ok(student);
    }
}