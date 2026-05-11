//WaitingStatus.java(유지 보수를 위한 파일 분리)
package com.capstone.registration;

public enum WaitingStatus {
    WAITING,       // 대기 중
    TRANSFERRED,   // 수강 확정으로 전환됨
    CANCELED       // 자동 취소(스마트 스왑 등)
}
