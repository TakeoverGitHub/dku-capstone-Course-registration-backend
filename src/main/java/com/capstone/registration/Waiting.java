//Waiting.java(상태 변경의 의도 명확화)
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
    public void transferToEnrollment() {
        this.status = WaitingStatus.TRANSFERRED;
    }

    public void cancel() {
        this.status = WaitingStatus.CANCELED;
    }
}
