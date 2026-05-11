package com.capstone.registration.controller;

import com.capstone.registration.dto.EnrollmentRequestDto;
import com.capstone.registration.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelCourse(@RequestBody EnrollmentRequestDto requestDto) {
        try {
            String result = enrollmentService.cancelEnrollment(requestDto);
            // 수강확정된 과목 취소
            return ResponseEntity.ok(Map.of("status", result, "message", "수강이 정상적으로 취소되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("status", "ERROR", "message", e.getMessage()));
        }
    }

    // 마이페이지 (내 수강 내역 조회) API: GET /api/enroll/mypage/{studentId}
    @GetMapping("/mypage/{studentId}")
    public ResponseEntity<?> getMyPage(@PathVariable String studentId) {
        try {
            Map<String, Object> myPageData = enrollmentService.getMyPage(studentId);
            return ResponseEntity.ok(myPageData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("status", "ERROR", "message", e.getMessage()));
        }
    }
}