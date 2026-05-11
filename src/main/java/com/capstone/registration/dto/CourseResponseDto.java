package com.capstone.registration.dto;

import com.capstone.registration.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseResponseDto {
    private Long courseId;
    private String courseCode;
    private String courseName;
    private int credit;
    private int maxCapacity;
    private int currentEnrollment;
    private String dayOfWeek;
    private int startTime;
    private int endTime;
    private int classNo;

    // 엔티티를 포장하는 메소드(화면에 출력할 데이터만 거름)
    public static CourseResponseDto from(Course course) {
        return new CourseResponseDto(
                course.getId(), course.getCourseCode(), course.getCourseName(),
                course.getCredit(), course.getMaxCapacity(), course.getCurrentEnrollment(),
                course.getDayOfWeek(), course.getStartTime(), course.getEndTime(), course.getClassNo()
        );
    }
}