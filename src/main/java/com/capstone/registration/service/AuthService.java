package com.capstone.registration.service;

import com.capstone.registration.Student;
import com.capstone.registration.repository.StudentRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;

    public Student authenticate(String studentId, String password) {
        // DB에서 학번으로 조회
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("해당 학번의 학생이 없습니다."));

        // 비밀번호 비교 (현재는 평문 비교, 보안을 위해선 BCrypt 권장)
        if (!student.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        return student;
    }
}