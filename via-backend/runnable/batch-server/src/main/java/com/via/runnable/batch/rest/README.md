# Batch API REST Package

## 📁 패키지 구조
```
rest/
├── controller/     # Batch Job을 트리거하는 REST API
└── http/           # JetBrains HTTP Client 호출 예시
```

## 🎯 개요

이 패키지는 Spring Batch Job을 HTTP API를 통해 수동으로 트리거할 수 있는 엔드포인트를 제공합니다.

## 📂 Controller

`controller` 패키지에는 각 Batch Job을 API 형태로 호출할 수 있는 컨트롤러가 정의되어 있습니다.

**제공되는 API:**
- `POST /api/common/sync/corporation` - 기업 정보 동기화
- `POST /api/common/sync/major` - 전공 정보 동기화
- `POST /api/common/sync/university` - 대학 정보 동기화

모든 API는 비동기로 실행되며, 즉시 `202 Accepted` 응답을 반환합니다.

## 📄 HTTP

`http` 패키지에는 JetBrains HTTP Client 형식으로 작성된 API 호출 예시가 포함되어 있습니다.

IntelliJ IDEA에서 `.http` 파일을 열어 각 요청 옆의 ▶️ 버튼을 클릭하거나 `Ctrl+Enter` (Windows) / `Cmd+Enter` (Mac)로 실행할 수 있습니다.

## 🚀 사용 방법

배치 어플리케이션이 운영 중인 EC2 서버에 접속하여 API를 호출할 수 있습니다.

**curl 명령어 예시:**
```bash
# 기업 정보 동기화
curl -X POST http://localhost:8081/api/common/sync/corporation

# 전공 정보 동기화
curl -X POST http://localhost:8081/api/common/sync/major

# 대학 정보 동기화
curl -X POST http://localhost:8081/api/common/sync/university
```

## 📝 참고사항

- 모든 API는 POST 메서드를 사용합니다.
- Job은 비동기로 실행되므로 API 응답은 Job 완료를 의미하지 않습니다.
- Job 실행 상태는 로그를 통해 확인할 수 있습니다.
- API를 중복 호출하면 동일한 Job이 여러 번 실행될 수 있으니 주의가 필요합니다.