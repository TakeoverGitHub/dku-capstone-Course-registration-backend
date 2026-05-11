package com.capstone.registration.dto;

import lombok.Getter;

@Getter
public class EnrollmentRequestDto {
    private String studentId; // 프론트에서 넘어올 학번
    private Long courseId;    // 프론트에서 넘어올 강의 ID
}