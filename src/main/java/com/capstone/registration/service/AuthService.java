package com.capstone.registration.service;

import com.capstone.registration.Student;
import com.capstone.registration.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;

    public Student authenticate(String studentId, String password) {
        // findByStudentId 대신 표준 이름인 findById 사용
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("해당 학번의 학생을 찾을 수 없습니다."));

        if (!student.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        return student;
    }
}