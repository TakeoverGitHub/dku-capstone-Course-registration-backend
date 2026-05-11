package com.capstone.registration.repository;

import com.capstone.registration.Course;
import org.springframework.data.jpa.repository.JpaRepository;

//DB테이블 매핑하여 쿼리 없이 데이터 CRUD
public interface CourseRepository extends JpaRepository<Course, Long> {

}