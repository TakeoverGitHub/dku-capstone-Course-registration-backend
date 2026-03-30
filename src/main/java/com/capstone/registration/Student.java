package com.capstone.registration;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "student_id", length = 20)
    private String studentId; // 학번 (PK)

    @Version
    private Long version;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "max_credit", nullable = false)
    private int maxCredit = 21; // 최대 수강 학점

    // 스마트 스왑 - 대기열 진입 시 이 학점이 먼저 깎임
    @Column(name = "available_credit", nullable = false)
    private int availableCredit = 21;

    @Builder
    public Student(String studentId, String password, String name, int maxCredit) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.maxCredit = maxCredit;
        this.availableCredit = maxCredit;
    }

    // 비즈니스 로직: 대기열/수강신청 시 학점 차감
    public void deductCredit(int credit) {
        if (this.availableCredit < credit) {
            throw new IllegalArgumentException("잔여 학점이 부족합니다. (현재: " + availableCredit + ")");
        }
        this.availableCredit -= credit;
    }

    // 비즈니스 로직 : 스마트 스왑(자동 취소) 발동 시 학점 반환
    public void refundCredit(int credit) {
        if (this.availableCredit + credit > this.maxCredit) {
            this.availableCredit = this.maxCredit;
        } else {
            this.availableCredit += credit;
        }
    }
}
