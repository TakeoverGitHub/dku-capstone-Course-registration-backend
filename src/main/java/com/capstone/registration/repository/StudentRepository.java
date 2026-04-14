package com.capstone.registration.repository;

import com.capstone.registration.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}