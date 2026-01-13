## 개요

### **주요 아키텍처 패턴**
- **멀티 모듈 Gradle** 프로젝트
- **헥사고날 아키텍처** (Ports & Adapters)
- **DDD (Domain-Driven Design)** 원칙 적용
- **마이크로서비스** 아키텍처 기반 모듈 설계
- **계층 분리**: Presentation (API) → Application → Domain ← Infrastructure

### **기술 스택**
- Java 21, Spring Boot 3.5.6
- MySQL, Redis(Valkey), OpenSearch
- OAuth2 (Google, Kakao), JWT 인증
- Docker/ECS, Spring Cloud, Spring AI
- QueryDSL, JPA

---

## I. 프로젝트 구조

### 1. 모듈 및 패키지 구조
```
via-backend/
├── core/                       # 공통 유틸리티, 예외 처리, 공통 인터페이스
│
├── setting/                    # 인프라 설정 모듈
│   ├── cache/                  # Redis 설정
│   ├── database/               # JPA/MySQL 설정 (QueryDSL 포함)
│   ├── storage/                # S3 등 스토리지 설정
│   ├── external/               # 외부 API 설정 (Feign)
│   ├── search/                 # OpenSearch 설정
│   └── crawler/                # 크롤링 설정
│
├── support/                    # 횡단 관심사(Cross-cutting concerns)
│   ├── security/               # Spring Security, OAuth2, JWT
│   └── swagger/                # API 문서화 (Swagger/OpenAPI)
│
├── runnable/                   # 실행 가능한 애플리케이션
│   ├── api-server/             # REST API 서버 (Spring Boot Application)
│   └── batch-server/           # 배치 작업 서버
│
├── {context}/                  # Bounded Context (DDD)
│   ├── {context}-api/          # Presentation Layer (Controller, DTO)
│   ├── {context}-domain/       # 도메인 모델 & 비즈니스 로직
│   ├── {context}-app/          # 유스케이스 & Application Service
│   ├── {context}-infra/        # 인프라 구현 (JPA, Redis, Feign, S3)
│   └── {context}-crawler/      # (선택) 크롤링 로직 (예: content)
│
└── common/                     # 미성숙 도메인 / 공유 도메인
    ├── common-api/
    ├── common-domain/
    ├── common-app/
    └── common-infra/
```

### 2. Context별 모듈 구성

프로젝트는 다음과 같은 **Bounded Context**로 구성됩니다:

| Context   | 설명                            | 모듈                                                                                    |
|-----------|-------------------------------|----------------------------------------------------------------------------------------|
| **account** | 사용자 계정, 인증/인가               | `account-api`, `account-domain`, `account-app`, `account-infra`                       |
| **content** | 컨텐츠 관리, 크롤링                 | `content-api`, `content-domain`, `content-app`, `content-infra`, `content-crawler`   |
| **common**  | 공통 도메인 (미성숙 도메인)            | `common-api`, `common-domain`, `common-app`, `common-infra`                          |

### 3. 계층 구조 및 의존성

```
┌─────────────────────────────────────────────────────────────┐
│                   Runnable Applications                     │
│                   (runnable:api-server)                     │
│                   - Spring Boot Main                        │
│                   - Server Configuration                    │
│                   - Application Entry Point                 │
└──────────────────────────┬──────────────────────────────────┘
                           │ depends on
┌──────────────────────────▼──────────────────────────────────┐
│                   Presentation Layer                        │
│                   ({context}-api)                           │
│                   - REST Controllers                        │
│                   - Request/Response DTOs                   │
│                   - API Endpoints                           │
└──────────────────────────┬──────────────────────────────────┘
                           │ depends on
┌──────────────────────────▼──────────────────────────────────┐
│                    Application Layer                        │
│                   ({context}-app)                           │
│                   - Use Cases                               │
│                   - Application Services                    │
│                   - Port Definitions (interfaces)           │
└──────────────┬───────────────────────┬──────────────────────┘
               │                       │
    depends on │                       │ depends on
               │                       │
┌──────────────▼──────────────┐  ┌────▼─────────────────────┐
│       Domain Layer          │  │  Infrastructure Layer    │
│    ({context}-domain)       │  │  ({context}-infra)       │
│    - Entities               │  │  - JPA Repositories      │
│    - Value Objects          │  │  - Redis Adapters        │
│    - Domain Services        │  │  - Feign Clients         │
│    - Domain Events          │  │  - S3 Adapters           │
└─────────────────────────────┘  └──────────────────────────┘
```

### 4. 모듈별 책임

| 레이어/모듈              | 패턴                  | 책임                                             |
|---------------------|---------------------|------------------------------------------------|
| **Runnable**        | `runnable:api-server` | Spring Boot 애플리케이션 실행, 서버 설정, Main 진입점         |
|                     | `runnable:batch-server` | 배치 작업 실행 애플리케이션                                |
| **Presentation**    | `{context}-api`     | HTTP 컨트롤러, Request/Response DTO, API 엔드포인트     |
| **Application**     | `{context}-app`     | 유스케이스 구현, 트랜잭션 관리, Port 인터페이스 정의               |
| **Domain**          | `{context}-domain`  | 핵심 비즈니스 로직, 엔티티, Value Object, 도메인 서비스         |
| **Infrastructure**  | `{context}-infra`   | DB/외부 API 구현체 (Adapter), JPA Repository, Feign |
| **Support**         | `support:*`         | 보안(Security), API 문서화(Swagger) 등 횡단 관심사        |
| **Setting**         | `setting:*`         | 인프라 설정 (DB, Cache, Storage, Search, Crawler)   |
| **Core**            | `core`              | 공통 유틸리티, 예외 처리, 공통 인터페이스                       |

### 5. 모듈 간 의존성 규칙

#### ✅ 허용된 의존성
- `runnable:api-server` → `{context}-api`, `support:*`, `core`
- `{context}-api` → `{context}-app`, `core`
- `{context}-app` → `{context}-domain`, `setting:*`, `core`
- `{context}-infra` → `{context}-domain`, `{context}-app`, `setting:*`, `core`
- `{context}-app` → 다른 `{other-context}-app` (Context 간 통신, 필요시)

#### ❌ 금지된 의존성
- `{context}-domain` → 다른 레이어 (도메인은 독립적이어야 함)
- `{context}-app` → `{context}-infra` (직접 의존 금지, Port/Adapter 패턴 사용)
- `runnable:api-server` → `{context}-app` (계층 우회 금지, API 레이어를 통해야 함)
- `runnable:api-server` → `{context}-infra` (계층 우회 금지)
- `{context}-api` → `{context}-infra` (계층 우회 금지)

---

## II. 로컬 환경 프로젝트 구동

### 1. 요구사항
```bash
# Java 21
java -version

# Docker & Docker Compose
docker --version
docker-compose --version

# Gradle
./gradlew --version
```

### 2. 로컬 환경 인프라 실행 (Docker Compose)

#### 1) 인프라 컨테이너 시작
```bash
# 프로젝트 루트에서 실행
cd ~\via-backend

# 백그라운드 실행
docker-compose up -d

# 로그 확인
docker-compose logs -f
```

#### 2) 컨테이너 상태 확인
```bash
docker-compose ps

# 예상 출력:
# via-mysql                 Up      0.0.0.0:13306->3306/tcp
# via-redis                 Up      0.0.0.0:16379->6379/tcp
# via-opensearch-node1      Up      0.0.0.0:9200->9200/tcp
# via-opensearch-node2      Up
# via-opensearch-dashboards Up      0.0.0.0:5601->5601/tcp
```

#### 3) 접속 정보

| 서비스                       | 포트    | 접속 URL                  | 계정 정보                |
|---------------------------|-------|--------------------------|----------------------|
| **MySQL**                 | 13306 | `localhost:13306`        | root / Xpsd1@idKs!3  |
| **Redis**                 | 16379 | `localhost:16379`        | (없음)                 |
| **OpenSearch**            | 9200  | `http://localhost:9200`  | admin / Xpsd1@idKs!3 |
| **OpenSearch Dashboards** | 5601  | `http://localhost:5601`  | admin / Xpsd1@idKs!3 |

### 3. 환경 변수 설정

프로젝트 **`.gitignore`에 `*.yml` 파일이 등록**되어 있습니다.  
**환경 설정 파일은 Git에 포함되지 않습니다**.

#### 1) 환경 변수 파일 위치
```
setting/
├── database/src/main/resources/database.yml
├── cache/src/main/resources/cache.yml
├── storage/src/main/resources/storage.yml
├── external/src/main/resources/external.yml
├── search/src/main/resources/search.yml
└── crawler/src/main/resources/crawler.yml
```

#### 2) 환경 변수 다운로드 링크
https://www.notion.so/asifceo/272fef828e5c806490d3eab657f96dfb?v=272fef828e5c819e9264000cfa31162c&source=copy_link

### 4. 애플리케이션 실행

#### 1) Gradle 빌드
```bash
# 전체 빌드
./gradlew clean build

# 테스트 제외 빌드
./gradlew clean build -x test
```

#### 2) API 서버 실행
```bash
# runnable:api-server 모듈 실행
./gradlew :runnable:api-server:bootRun

# 또는 IDE에서 ApiServerApplication.java 실행
```

#### 3) Swagger UI 접속
```
http://localhost:8080/swagger-ui/index.html
```

---

## III. 프로젝트 기여

### 1. 코드 작성

#### 1) 이슈 생성
```
GitHub Issues > New Issue

Title: [Feature] 사용자 프로필 조회 API 추가
Description:
- 사용자 ID로 프로필 정보 조회
- 닉네임, 이메일, 이미지 반환
- JWT 인증 필요
```

#### 2) 브랜치 생성 및 개발
```bash
git checkout -b feature/user-profile-api
```

#### 3) 커밋 컨벤션
```bash
# 권장 커밋 메시지 형식 (Conventional Commits)
git commit -m "feat: 사용자 프로필 조회 API 추가"
git commit -m "fix: OAuth2 토큰 갱신 버그 수정"
git commit -m "docs: 프로젝트 실행 가이드 추가"
git commit -m "refactor: 닉네임 생성 로직 개선"
git commit -m "test: 로그인 서비스 단위 테스트 추가"

# 타입 목록:
# feat: 새로운 기능
# fix: 버그 수정
# docs: 문서 변경
# style: 코드 포맷팅
# refactor: 리팩토링
# test: 테스트 추가
# chore: 빌드/설정 변경
```

#### 4) Push 및 PR 생성
```bash
git push origin feature/user-profile-api

# GitHub에서 Pull Request 생성
# Base: main <- Compare: feature/user-profile-api
```

### 2. Pull Request

#### PR 템플릿 (권장)
```markdown
## 📝 변경 사항
- 사용자 프로필 조회 API 추가 (`GET /api/v1/users/{userId}`)
- UserProfileResponse DTO 추가

## 🔗 관련 이슈
- Closes #123

## ✅ 체크리스트
- [x] 코드 작성 완료
- [x] 단위 테스트 작성
- [x] Swagger 문서화
- [x] 로컬 환경 테스트

## 🚨 Breaking Changes
없음
```

### 3. 테스트 (CI 자동화 예정)

#### 전체 테스트
```bash
./gradlew test
```

#### 특정 모듈 테스트
```bash
./gradlew :account:account-app:test
./gradlew :content:content-domain:test
```

#### 테스트 커버리지 확인
```bash
./gradlew jacocoTestReport

# 결과 확인
# build/reports/jacoco/test/html/index.html
```

### 4. 데이터베이스 스키마 변경

#### 1) SQL 파일 추가
```sql
-- setting/database/src/main/resources/initdb.d/XX-CreateNewTable.sql
use account;

CREATE TABLE new_feature (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) DEFAULT CHARACTER SET utf8mb4;
```

#### 2) 데이터베이스 재시작
```bash
docker-compose down -v
docker-compose up -d
```

### 5. 새로운 Context 추가 가이드

새로운 Bounded Context를 추가할 때는 다음 단계를 따릅니다:

#### 1) 모듈 구조 생성
```
{new-context}/
├── {new-context}-api/       # Presentation Layer (Controller)
├── {new-context}-domain/    # 도메인 모델
├── {new-context}-app/       # 애플리케이션 서비스
└── {new-context}-infra/     # 인프라 구현
```

#### 2) settings.gradle에 모듈 등록
```groovy
include(
    '{new-context}:{new-context}-api',
    '{new-context}:{new-context}-domain',
    '{new-context}:{new-context}-app',
    '{new-context}:{new-context}-infra',
)
```

#### 3) 각 모듈의 build.gradle 작성
```groovy
// {new-context}-domain/build.gradle
dependencies {
    implementation project(':core')
    // domain은 다른 레이어에 의존하지 않음
}

// {new-context}-app/build.gradle
dependencies {
    implementation project(':core')
    implementation project(':{new-context}:{new-context}-domain')
    implementation project(':setting:database')
    // 필요한 setting 모듈들 추가
}

// {new-context}-infra/build.gradle
dependencies {
    implementation project(':core')
    implementation project(':{new-context}:{new-context}-domain')
    implementation project(':{new-context}:{new-context}-app')
    implementation project(':setting:database')
    // 필요한 setting 모듈들 추가
}

// {new-context}-api/build.gradle
dependencies {
    implementation project(':core')
    implementation project(':{new-context}:{new-context}-app')
}
```

#### 4) runnable:api-server에 의존성 추가
```groovy
// runnable/api-server/build.gradle
dependencies {
    // ... 기존 의존성
    implementation project(':{new-context}:{new-context}-api')
}
```

---

## IV. 아키텍처 원칙

### 1. 헥사고날 아키텍처 (Ports & Adapters)

#### Port (인터페이스 정의)
- `{context}-app` 모듈에서 정의
- 외부 의존성에 대한 추상화

```java
// account:account-app 모듈
public interface MemberRepository {  // Port
    Member save(Member member);
    Optional<Member> findById(MemberId id);
}
```

#### Adapter (구현체)
- `{context}-infra` 모듈에서 구현
- JPA, Redis, Feign 등 실제 기술 구현

```java
// account:account-infra 모듈
@Repository
public class MemberRepositoryAdapter implements MemberRepository {  // Adapter
    private final MemberJpaRepository jpaRepository;
    
    @Override
    public Member save(Member member) {
        MemberEntity entity = MemberMapper.toEntity(member);
        return MemberMapper.toDomain(jpaRepository.save(entity));
    }
}
```

### 2. DDD (Domain-Driven Design)

#### Bounded Context
각 Context는 독립적인 도메인 모델을 가지며, 명확한 경계를 유지합니다.

#### Aggregate
- 엔티티 그룹의 루트
- 일관성 경계 정의
- 트랜잭션 범위 결정

```java
// account:account-domain 모듈
public class Member {  // Aggregate Root
    private MemberId id;
    private Email email;
    private Nickname nickname;
    private MemberProfile profile;  // Entity
    
    // 비즈니스 로직은 도메인에 위치
    public void changeNickname(Nickname newNickname) {
        if (this.nickname.equals(newNickname)) {
            throw new DuplicateNicknameException();
        }
        this.nickname = newNickname;
    }
}
```

### 3. Context 간 통신

#### Application Layer를 통한 통신 (Port 모듈 혹은 EDD 도입 검토 중)
각 Context는 다른 Context의 Application Layer를 통해 통신합니다.

```java
// content:content-api 모듈 (Presentation Layer)
@RestController
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;  // content-app
    
    @GetMapping("/contents/{contentId}")
    public ContentDetailResponse getContentDetail(@PathVariable Long contentId) {
        return contentService.getContentDetail(contentId);
    }
}

// content:content-app 모듈 (Application Layer)
@Service
@RequiredArgsConstructor
public class ContentService {
    private final MemberService memberService;  // account-app에서 제공
    private final ContentRepository contentRepository;
    
    public ContentDetailResponse getContentDetail(Long contentId) {
        Content content = contentRepository.findById(contentId);
        
        // account context와 통신
        MemberDto member = memberService.getMember(content.getAuthor().getMemberId());
        
        return ContentDetailResponse.of(content, member);
    }
}
```

---

## V. QueryDSL 사용 가이드

### 1. Q-Class 생성

QueryDSL Q-Class는 `{context}:{context}-infra` 모듈에서 context 별로 관리됩니다.

```bash
# Q-Class 생성
./gradlew :{context}:{context}-infra:compileJava

# 전체 빌드 시 자동 생성
./gradlew clean build
```

### 2. QueryDSL 사용 예시

```java
// account:account-infra 모듈
@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final JPAQueryFactory queryFactory;
    
    public List<Member> findActiveMembers() {
        return queryFactory
            .selectFrom(QMemberEntity.memberEntity)
            .where(QMemberEntity.memberEntity.status.eq(MemberStatus.ACTIVE))
            .fetch();
    }
}
```

---

## VI. Custom Annotations

### 1. @CurrentUserId

현재 인증된 사용자의 ID를 파라미터로 주입합니다.
```java
@GetMapping("/profile")
public UserProfile getProfile(@CurrentUserId Long userId) {
    return userService.getProfile(userId);
}
```

### 2. @RequireAuthority

특정 권한을 가진 사용자만 접근 가능하도록 제한합니다.
```java
@RequireAuthority({Authority.ADMIN})
public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
}

@RequireAuthority({Authority.ADMIN, Authority.MANAGER})
public void approveRequest(Long requestId) {
    requestService.approve(requestId);
}
```

**속성**
- `value`: 필요한 권한 배열 (필수, OR 조건)

### 3. @GlobalCacheable

메서드 결과를 캐싱합니다.
```java
@GlobalCacheable(cacheName = "User", key = "#userId", ttl = 3600)
public User getUser(Long userId) {
    return userRepository.findById(userId);
}
```

**속성**
- `cacheName`: 캐시 이름 (필수)
- `key`: SpEL 표현식 (선택, 기본값: "")
- `ttl`: 유효 시간(초) (선택, 기본값: 3600)

**SpEL 예제**
```java
// 단일 파라미터
@GlobalCacheable(cacheName = "User", key = "#userId")
// → Redis Key: "User:123"

// 복합 키
@GlobalCacheable(cacheName = "Order", key = "#userId + ':' + #orderId")
// → Redis Key: "Order:123:456"

// 객체 필드
@GlobalCacheable(cacheName = "Product", key = "#request.categoryId")
// → Redis Key: "Product:10"

// key 없음
@GlobalCacheable(cacheName = "TermsInfo")
// → Redis Key: "TermsInfo"
```

### 4. @GlobalCacheEvict

캐시를 삭제합니다.
```java
@GlobalCacheEvict(cacheName = "User", key = "#userId")
public void updateUser(Long userId) {
    userRepository.update(userId);
}
```

**속성**
- `cacheName`: 캐시 이름 (필수)
- `key`: SpEL 표현식 (선택, 기본값: "")

**SpEL 예제**
```java
// 특정 키 삭제
@GlobalCacheEvict(cacheName = "User", key = "#userId")
// → "User:123" 삭제

// cacheName의 모든 키 삭제 (key 없음)
@GlobalCacheEvict(cacheName = "User")
// → "User:*" 패턴의 모든 키 삭제
```

### 5. @GlobalLock

분산 환경에서 동시성 제어를 위한 분산 락을 제공합니다.

```java
@GlobalLock(lockName = "course", key = "#courseId", waitTime = 5, leaseTime = 10)
public void updateCourse(Long courseId) {
    courseRepository.update(courseId);
}
```

**속성**
- `lockName`: 락 이름 (필수)
- `key`: SpEL 표현식 (선택, 기본값: "")
- `timeUnit`: 시간 단위 (선택, 기본값: TimeUnit.SECONDS)
- `waitTime`: 락 획득 대기 시간 (선택, 기본값: 5L)
- `leaseTime`: 락 보유 최대 시간 (선택, 기본값: 5L)

**SpEL 예제**
```java
// 단일 파라미터
@GlobalLock(lockName = "course", key = "#courseId")
// → Redis Lock Key: "course:123"

// 복합 키
@GlobalLock(lockName = "enrollment", key = "#userId + ':' + #courseId")
// → Redis Lock Key: "enrollment:123:456"

// 객체 필드
@GlobalLock(lockName = "payment", key = "#request.orderId")
// → Redis Lock Key: "payment:789"

// key 없음
@GlobalLock(lockName = "batch-sync")
// → Redis Lock Key: "batch-sync"
```

**시간 설정 예제**
```java
// 기본 설정 (5초 대기, 5초 후 자동 해제)
@GlobalLock(lockName = "user", key = "#userId")

// 커스텀 시간 (3초 대기, 10초 후 자동 해제)
@GlobalLock(lockName = "course", key = "#courseId", waitTime = 3, leaseTime = 10)

// 밀리초 단위
@GlobalLock(
    lockName = "payment", 
    key = "#paymentId", 
    waitTime = 3000, 
    leaseTime = 5000,
    timeUnit = TimeUnit.MILLISECONDS
)
```