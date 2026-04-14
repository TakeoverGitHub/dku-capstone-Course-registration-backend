package com.capstone.registration.service;

import com.capstone.registration.dto.CourseResponseDto;
import com.capstone.registration.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 선언, 오류 발생시 롤백 기능
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseResponseDto> getAllCourses() {
        return courseRepository.findAll().stream() // Course 데이터 객체(강의) 단위로 스트림화
                .map(CourseResponseDto::from) // 가져온 강의들을 각각 DTO로 포장
                .collect(Collectors.toList()); // 리스트로 변환
    }
}