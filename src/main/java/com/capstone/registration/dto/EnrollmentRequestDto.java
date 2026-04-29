package com.capstone.registration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnrollmentRequestDto {
    private String studentId; // 프론트에서 넘어올 학번
    private Long courseId;    // 프론트에서 넘어올 강의 ID
    private String password; // 프론트에서 넘어올 패스워드
}