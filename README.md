# 🎓 예비번호 기반 수강신청 자동화 시스템 (Backend)



## 1. 📝 프로젝트 소개

기존 수강신청 시스템의 '취소 지연제'로 인한 무한 새로고침 불편함을 해결하기 위한 **단국대학교 수강신청 개선 프로젝트**의 백엔드 저장소입니다.

정원이 마감된 과목에 예비번호(대기열)를 도입하고, 취소자 발생 시 대기 순번에 따라 자동으로 수강신청이 처리되는 시스템의 핵심 비즈니스 로직과 API를 제공합니다.



## 2. 🛠 기술 스택

- **Language:** Java 17

- **Framework:** Spring Boot 3.x

- **Database:** MySQL, Spring Data JPA

- **Build Tool:** Gradle



## 3. ⚙️ 주요 기능 (Backend 핵심 로직)

- **회원 인증:** 학번 기반 회원가입 및 로그인 API

- **수강신청 로직:** 과목별 여석 확인 및 즉시 확정 처리

- **대기열(예비번호) 시스템:** 여석 마감 시 대기열 등록 및 실시간 순번 부여 로직

- **자동 수강 확정 처리:** 기존 수강생 취소 시 대기열 1순위 자동 확정 전환 및 동시성(Concurrency) 제어



## 4. 🚀 시작하기 (Getting Started)

프로젝트를 로컬 환경에서 실행하기 위한 가이드입니다.



### 필수 조건 (Prerequisites)

- Java 17

- MySQL 8.0 이상



### 실행 방법

1. 저장소를 클론(Clone) 또는 포크(Fork) 합니다.

   > git clone [본인의 Fork 레포지토리 주소]



2. 로컬 DB 세팅: MySQL에 `registration` 데이터베이스를 생성합니다.

3. `src/main/resources/application.properties` (또는 yml) 파일에 로컬 DB 계정 정보를 알맞게 수정합니다.

4. IDE(IntelliJ 등)에서 프로젝트를 열고 `RegistrationApplication.java`를 실행합니다.



## 5. 👨‍💻 Backend 팀원

- **박재성:** DB 설계, 대기열 및 동시성 제어 로직 개발, 공통 API 구현

- **[팀원 2 이름]:** [담당할 역할 간략히 기재]