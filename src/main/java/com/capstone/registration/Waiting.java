package com.capstone.registration;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "waiting")
public class Waiting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waiting_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // 대기 상태 (WAITING, TRANSFERRED, CANCELED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WaitingStatus status;

    // 예비번호 우선순위를 결정짓는 기준 시간
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Waiting(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.status = WaitingStatus.WAITING;
        this.createdAt = LocalDateTime.now();
    }

    // 비즈니스 로직: 상태 변경 (예: 취소자 발생 시 TRANSFERRED로 변경)
    public void updateStatus(WaitingStatus status) {
        this.status = status;
    }
}

// ⭐ 상태 관리를 위한 Enum
enum WaitingStatus {
    WAITING,       // 대기 중
    TRANSFERRED,   // 여석 발생으로 수강 확정(Enrollment)으로 넘어감
    CANCELED       // 스마트 스왑 발동 - 타 과목 확정으로 인해 대기 자동 이탈
}