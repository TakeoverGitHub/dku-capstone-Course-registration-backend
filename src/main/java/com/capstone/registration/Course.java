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

    @Column
    private int grade; // 학년

    @Column(nullable = false)
    private int credit; // 이수 학점 (예: 3학점)

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity; // 총 정원

    @Column(nullable = false)
    private int wish; // 희망 인원 (장바구니 담은 수)

    // 수강 신청 성공 시 증가할 현재 인원
    @Column(name = "current_enrollment", nullable = false)
    private int currentEnrollment = 0;

    @Column(nullable = false)
    private int remain; // 잔여석

    @Column(name = "day_of_week", length = 10)
    private String dayOfWeek; // 요일 (MON, TUE 등)

    @Column(name = "start_time")
    private Integer startTime; // 시작 교시 (예: 1)

    @Column(name = "end_time")
    private Integer endTime; // 종료 교시 (예: 3)

    @Column(nullable = false)
    private String campus; // 캠퍼스

    @Column
    private String major; // 전공명

    @Column
    private String category; // 전공필수, 전공선택, 필수교양, 선택교양 등 구분

    @Column(name = "course_type", nullable = false)
    private String courseType; // 강의 종류 (전공, 교양, 학문기초)

    @Builder
    public Course(String courseCode, int classNo, String courseName, int credit, int maxCapacity, String dayOfWeek, int startTime, int endTime,
                  int wish, int remain, int grade, String campus, String major, String category, String courseType) {
        this.courseCode = courseCode;
        this.classNo = classNo;
        this.courseName = courseName;
        this.credit = credit;
        this.maxCapacity = maxCapacity;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentEnrollment = 0; // 초기 수강 인원은 0명!
        this.wish = wish;
        this.remain = remain;
        this.grade = grade;
        this.campus = campus;
        this.major = major;
        this.category = category;
        this.courseType = courseType;
    }

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
