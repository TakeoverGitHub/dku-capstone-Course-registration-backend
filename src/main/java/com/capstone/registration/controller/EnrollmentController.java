package com.capstone.registration.controller;

import com.capstone.registration.dto.EnrollmentRequestDto;
import com.capstone.registration.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/enroll")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<?> enrollCourse(@RequestBody EnrollmentRequestDto requestDto) {
        try {
            String result = enrollmentService.enroll(requestDto);

            // 프론트엔드에게 성공(SUCCESS) 또는 대기(WAITING) 상태 전달
            return ResponseEntity.ok(Map.of("status", result, "message", "요청이 처리되었습니다."));
        } catch (Exception e) {
            // 학점 부족 등 에러 발생 시 처리
            return ResponseEntity.badRequest().body(Map.of("status", "ERROR", "message", e.getMessage()));
        }
    }
}