package com.capstone.registration.repository;

import java.util.List;
import com.capstone.registration.Waiting;
import com.capstone.registration.WaitingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {
    // 특정 과목(courseId)의 대기열 중 가장 먼저 신청한(CreatedAt 기준 오름차순) 1명 가져오기
    Optional<Waiting> findFirstByCourse_IdAndStatusOrderByCreatedAtAsc(Long courseId, WaitingStatus status);
    // 특정 학생의 특정 상태(예: WAITING) 대기 내역 모두 찾기
    List<Waiting> findByStudent_StudentIdAndStatus(String studentId, WaitingStatus status);
}