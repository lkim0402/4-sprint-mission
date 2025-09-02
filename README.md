# 0-spring-mission

스프린트 미션 모범 답안 리포지토리입니다.
이 프로젝트는 **실시간 채팅 애플리케이션**을 구현한 Spring Boot 기반 백엔드 서비스입니다. 사용자가 메시지와 파일을 주고받을 수 있으며, 안정적인 데이터 저장과 확장 가능한 아키텍처를 제공합니다.  

# 시스템 다이어그램
<img width="965" height="544" alt="image" src="https://github.com/user-attachments/assets/b7723288-7c80-4131-b181-eaacfae0e094" />

- Layered Architecture 기반 Spring Boot 애플리케이션
- 데이터 저장
  - RDS(PostgreSQL) 연동 (엔티티 데이터 저장)
  - S3 연동 (파일 업로드)
- Docker + Docker Compose로 컨테이너화 → ECR 업로드 후 ECS 배포
- GitHub Actions CI/CD 파이프라인으로 테스트 → 빌드 → 배포 자동화
- 최종적으로 EC2 퍼블릭 주소를 통해 서비스 접근 가능

# 테스트 커버리지
- JUnit5, Mockito 사용
- Jacoco: 코드 커버리지 측정용으로 사용

[![codecov](https://codecov.io/gh/lkim0402/4-sprint-mission/graph/badge.svg)](https://codecov.io/gh/lkim0402/4-sprint-mission)
