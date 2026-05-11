package com.capstone.registration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendEnrollmentSuccessEmail(String toEmail, String studentName, String courseName) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail); // 수신자 (팀원 이메일)
        message.setSubject("🎉 [수강신청 알림] 빈자리가 발생하여 수강 확정되었습니다!"); // 제목
        message.setText(
                "안녕하세요, " + studentName + " 학생!\n\n" +
                        "대기 중이던 [" + courseName + "] 과목에 빈자리가 발생하여 스마트 스왑이 작동했습니다.\n" +
                        "성공적으로 수강이 확정되었으니 마이페이지에서 확인해 주세요.\n\n" +
                        "감사합니다."
        ); // 내용

        emailSender.send(message); // 실제 발송!
    }
}