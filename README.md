# Ramos_In_California-BE

## 📌 프로젝트 목적
2022년 여름, 미국 현지에서의 경험을 조금 특별하게 추억하고자 만들어 보는 포토 블로그입니다. 출국 전 개발 및 배포 일정을 계획하였으나, 귀국 후 공부 목적으로 남은 기능을 개발하려 합니다.

백엔드 프로젝트의 주요 목적은 **클린코드 작성 및 TDD 연습 목적으로 구현하는 Spring Boot 기반 REST API 서버입니다.** 프론트엔드는 Vue.js를 사용하여 최대한 간단하게나마 따로 구현하고자 합니다.

→ [프론트엔드 프로젝트 Repository](https://github.com/alanhakhyeonsong/Ramos_In_California-FE)

지난 한 학기 동안 캡스톤 프로젝트를 하며 다음과 같은 아쉬웠던 점을 해소하고자 하는 목적이 강합니다.
- 선 기능 구현 후 테스트 코드 작성으로 개발하면 객체지향이 깨질 수 밖에 없다. 그 결과 레거시 코드를 테스트하고자 하면 당연히 실패한다.
- 객체 간의 역할을 단순하고 분명히 하자. 메소드도 마찬가지다.
- 내부 flow 자체를 하나하나 이해하며 구현했다는 보장이 있는가? 기능 구현 중심적 사고가 아닌 객체지향적 사고를 하면 답을 찾을 수 있지 않을까?

## 🛠 Used Stack
- Java 11, Spring Boot 2.7.x
- Spring MVC, Spring Security, Spring Data JPA, QueryDSL
- Database: H2, MySQL, Redis
- Infra: Docker, AWS EC2, AWS S3, AWS RDS
- Test: JUnit 5, Mockito

## 구현 요구 사항
- Spring Security, JWT 인증/인가 + Redis 토큰 저장소
- AWS S3로 이미지 파일 업로드
- 계층형 권한으로 Admin 계정만 게시글 CRUD 및 댓글 작성이 가능하고, 일반 사용자는 댓글 작성까지만 허용한다. 익명 사용자는 게시글을 보는 것만 가능하다.
- 게시글 목록에 대해 페이징 처리
- 댓글에 대댓글 기능으로 총 2 depth
- HTTPS를 설정하여 배포