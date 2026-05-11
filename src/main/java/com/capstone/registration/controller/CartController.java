package com.capstone.registration.controller;

import com.capstone.registration.dto.CourseResponseDto;
import com.capstone.registration.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 담기
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, Object> request) {
        cartService.addToCart((String) request.get("studentId"), Long.valueOf(request.get("courseId").toString()));
        return ResponseEntity.ok(Map.of("message", "장바구니에 담겼습니다."));
    }

    // 내 장바구니 목록 조회 (수강신청 페이지에서 이걸 호출하면 됨)
    @GetMapping("/{studentId}")
    public ResponseEntity<List<CourseResponseDto>> getCart(@PathVariable String studentId) {
        return ResponseEntity.ok(cartService.getMyCart(studentId));
    }
}