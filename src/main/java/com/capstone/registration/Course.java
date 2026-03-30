package com.capstone.registration;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "course")
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Version
    private Long version;

    @Column(name = "course_code", nullable = false, length = 20)
    private String courseCode; // 과목 코드 (예: CS101)

    @Column(name = "class_no", nullable = false)
    private int classNo; // 분반 (예: 1, 2분반)

    @Column(name = "course_name", nullable = false)
    private String courseName; // 강의명

    @Column(nullable = false)
    private int credit; // 이수 학점 (예: 3학점)

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity; // 총 정원

    // 수강 신청 성공 시 증가할 현재 인원
    @Column(name = "current_enrollment", nullable = false)
    private int currentEnrollment = 0;

    @Column(name = "day_of_week", length = 10)
    private String dayOfWeek; // 요일 (MON, TUE 등)

    @Column(name = "start_time")
    private int startTime; // 시작 교시 (예: 1)

    @Column(name = "end_time")
    private int endTime; // 종료 교시 (예: 3)

    // 비즈니스 로직: 수강 인원 1명 증가 (Lock 걸고 실행할 메서드)
    public void increaseEnrollment() {
        if (this.currentEnrollment >= this.maxCapacity) {
            throw new IllegalStateException("수강 정원이 초과되었습니다.");
        }
        this.currentEnrollment++;
    }

    public void decreaseEnrollment() {
        if (this.currentEnrollment <= 0) {
            throw new IllegalStateException("현재 인원이 0명입니다.");
        }
        this.currentEnrollment--;
    }
}
