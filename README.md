# 얼마고 : 중고 물품 경매 플랫폼
**얼마고**는 원하는 중고 상품을 빠르고 정확한 검색으로 찾고, 실시간 경매로 합리적인 가격에 낙찰받을 수 있는 플랫폼입니다.<br>
판매자 정보와 거래 이력을 확인할 수 있고, 신고·제재 기반 운영으로 더 안전한 거래 환경을 제공합니다.<br>
경매와 거래 진행 상황을 알림으로 실시간 추적하고, 중요한 업데이트를 즉시 확인할 수 있습니다.<br>
낙찰 후에는 1:1 채팅방에서 판매자와 바로 소통하고, 거래를 안전하게 마무리할 수 있습니다.

| | |
| --- | --- |
| <img src="https://lpjncdsaqnkfjnhadodz.supabase.co/storage/v1/object/public/eolmago/view/home.png" width="100%" alt="홈 화면" /> | <img src="https://lpjncdsaqnkfjnhadodz.supabase.co/storage/v1/object/public/eolmago/view/detail.png" width="100%" alt="경매 상세 화면" /> |

<p align="center"><i>홈 / 경매 상세 화면</i></p>

<br>

## 배포 링크
<a href="https://aibe4-project2-team5-connect5.onrender.com/">
  <img
    src="https://lpjncdsaqnkfjnhadodz.supabase.co/storage/v1/object/public/eolmago/logo.png"
    alt="얼마고"
    width="220"
  />
</a>

[배포 사이트 바로가기](https://aibe4-project2-team5-connect5.onrender.com/)

<br>

## 기술 스택

### Backend
- Java 17, Spring Boot 3.5.9
- Spring Data JPA, QueryDSL
- Spring Security (OAuth2)
- PostgreSQL
- Redis
- WebSocket (STOMP)
- Swagger UI
- Prometheus, Grafana

### Frontend
- Thymeleaf
- Tailwind CSS

### Infra
- Docker
- GitHub Actions
- Render
- Supabase

<br>

## 주요 기능

### 가입 · 인증
- Google / Kakao / Naver 소셜 로그인
- SMS 전화번호 인증
- JWT 기반 인증 및 Role 권한 관리 (Spring Security, OAuth2)

### 경매
- 경매 임시저장 · 수정 · 삭제 · 게시 · 취소
- 내 경매 대시보드: 상태별 현황 및 성과
- 실시간 입찰: 최고가 실시간 갱신, 마감 5분 전 자동 연장(30분 캡)
- 입찰 동시성 제어: Redisson 분산락 + Redis Streams 기반 처리
- 경매 자동 마감: 스케줄링 기반 유찰/낙찰 처리, 낙찰 시 거래 자동 생성
- 유찰 시 재등록 기능 제공
- 권한별 입찰 내역 조회
- 판매자 정보(신뢰도) 확인

### 검색 · 자동완성
- 초성/일반 텍스트/오타 보정 검색
- 인기 키워드 자동완성
- Redis 기반 검색어 캐싱 및 인기 검색어 집계/정렬
- 카테고리·브랜드·가격·상태 다차원 필터링

### 거래 관리 · 확정서
- 거래 생성 및 상태 관리, 판매/구매 내역 분리 조회
- 판매자·구매자 상호 확정 후 거래 완료 처리
- 거래 확정서 PDF 생성 및 다운로드

### 알림 · 채팅
- 경매 · 입찰 · 낙찰 · 거래 · 신고 · 찜 실시간 알림 (SSE)
- 시스템 알림 타임라인: 내역 누적 조회 · 일괄 읽음 처리
- 거래 전용 1:1 실시간 채팅 (WebSocket · STOMP)
- Redis Streams 기반 메시지 처리/저장 파이프라인

### 신고 · 관리자
- 경매 게시물 및 사용자 신고 접수/조회
- 관리자 대시보드: 사용자 목록/상태 관리, 신고 검토·처리, 제재 관리

### 마이페이지
- 프로필 조회/수정 및 프로필 이미지 변경
- 찜한 경매 목록 조회
- 거래 통계(거래 횟수, 평균 별점 등) 조회

### 리뷰
- 거래 완료 후 리뷰 작성 · 수정 · 삭제
- 작성한 리뷰 목록 조회
- 받은 리뷰 목록 조회

<br>

## 내가 구현한 기능

### 🔐 인증 · 인가
- **Spring Security + OAuth2** 기반 소셜 로그인 (Google, Kakao, Naver)
- **JWT** 기반 토큰 인증 시스템
  - AccessToken / RefreshToken 분리 관리
  - Redis를 활용한 RefreshToken 저장 및 검증
  - 토큰 갱신(Rotate) 및 만료 처리
- **CoolSMS API** 연동 SMS 전화번호 인증
  - Redis 기반 인증코드 임시 저장 및 만료 처리
  - SecureRandom을 사용한 안전한 인증코드 생성

### 👤 유저 관리
- 회원 프로필 조회 및 수정
- **Supabase Storage** 연동 프로필 이미지 업로드
  - 이미지 파일 검증 (크기, 형식, 해상도)
  - 비동기 업로드 지원 (@Async)
- 사용자 제재 시스템 (정지, 영구 정지)
- 제재 이력 관리 및 조회

### 🛒 거래 관리
- 판매자/구매자별 거래 목록 조회 및 상태 관리
- 거래 상세 페이지 (Thymeleaf 기반 뷰)
- 거래 확정 및 완료 처리

### 🚨 신고 시스템
- 경매 게시물 및 사용자 신고 접수
- 신고 사유 카테고리별 분류
- 신고 상태 관리 (접수, 처리 중, 완료)

### 👨‍💼 관리자 페이지
- **QueryDSL** 기반 동적 쿼리를 활용한 고급 필터링
  - 사용자 목록 조회 (이름, 이메일, 상태별 필터)
  - 신고 목록 조회 (상태별 필터)
  - 제재 이력 조회 (유형별 필터)
- 사용자 상태 변경 (활성, 정지, 영구 정지)
- 신고 검토 및 처리 (경고, 정지, 영구 정지)
- **@PreAuthorize** 기반 ADMIN 권한 검증

### 🛠 기술 스택
- **Backend**: Spring Security, OAuth2, JJWT, Redis, QueryDSL
- **Frontend**: Thymeleaf, Tailwind CSS
- **External API**: CoolSMS (SMS 인증), Supabase Storage (이미지 업로드)

<br>

## ERD

<img width="3720" height="2192" alt="얼마고_ERD" src="https://github.com/user-attachments/assets/86893a21-8c23-48ff-b079-c131bea26d5e" />

[ERDCloud 바로가기](https://www.erdcloud.com/d/SHAtwxTSaqmk47nFJ)


<br>

## 소프트웨어 아키텍처



<br>

## 프로젝트 구조

```
kr.eolmago
├── controller
│   ├── api          # REST API 컨트롤러
│   └── view         # 뷰 렌더링 컨트롤러
├── dto
│   ├── api
│   │   ├── request  # API 요청 DTO
│   │   └── response # API 응답 DTO
│   └── view         # 뷰 전용 DTO
├── domain
│   └── entity       # JPA 엔티티
├── repository       # Spring Data JPA Repository
├── service          # 비즈니스 로직
└── global
    ├── config       # 설정 클래스
    ├── exception    # 예외 처리
    ├── handler      # 이벤트 핸들러
    └── util         # 유틸리티
```

<br>

## 트러블슈팅

### 스프링 시큐리티 JWT 인증 세션 이탈(로그인 풀림) 이슈 해결

#### 문제 상황
accessToken과 refreshToken이 쿠키에 존재함에도 불구하고 페이지 이동 시 간헐적으로 로그인이 풀리는 현상 발생. 사용자가 정상적으로 로그인했지만, 특정 페이지로 이동하면 인증이 해제되어 다시 로그인해야 하는 문제.

#### 원인
**1. Thymeleaf 템플릿 렌더링 오류**
- `base.html`에서 `th:attr` 문법 오류로 전체 페이지 렌더링 실패
- `th:attr`에 `class` 속성을 포함시켜 Thymeleaf 파싱 에러 발생
  ```html
  <!-- 문제 코드 -->
  <body th:attr="class='...', data-user-role=..., data-user-status=..., data-user-id=...">
  ```

**2. 렌더링 실패로 인한 부작용**
- HTML이 제대로 렌더링되지 않아 쿠키나 인증 정보 손실
- 브라우저에서 incomplete 응답 수신 (`ERR_INCOMPLETE_CHUNKED_ENCODING`)

#### 해결 방법
**1. Thymeleaf 문법 수정**
- `class` 속성을 `th:attr` 밖으로 분리
- `data` 속성만 `th:attr`로 처리
  ```html
  <!-- 수정 후 -->
  <body class="bg-gradient-to-br from-gray-50 via-white to-gray-50 text-gray-900 min-h-screen flex flex-col"
        th:attr="data-user-role=${userRole ?: 'ANONYMOUS'},
                 data-user-status=${userStatus ?: ''},
                 data-user-id=${userId ?: ''}">
  ```

**2. NavModelAdvice 검증**
- `GlobalControllerAdvice`를 제거하고 `NavModelAdvice`로 대체
- `@AuthenticationPrincipal`을 활용하여 효율적으로 개선
- `userRole`, `userStatus`, `userId` 등 전역 모델 속성이 정상적으로 설정되는지 확인
- 로그를 통해 각 요청마다 올바른 값이 전달되는지 검증

**3. JwtAuthenticationFilter 로깅 강화**
- 토큰 상태, 갱신 과정 등에 대한 상세 로그 추가
- 문제 발생 시점 추적 가능하도록 개선

**4. SecurityConfig 수정**
- `JwtAuthenticationFilter`를 `webChain`에도 추가하여 뷰 요청 시에도 JWT 인증 처리 보장

#### 결과
- 페이지 렌더링 정상화로 로그인 세션 유지
- 간헐적 로그인 풀림 현상 완전 해결

#### 교훈
- Thymeleaf 템플릿 문법 오류가 예상치 못한 인증 문제를 야기할 수 있음
- 프론트엔드와 백엔드 간 경계에서 발생하는 문제는 양쪽 모두 검증 필요
- 브라우저 개발자 도구의 Network 탭과 서버 로그를 함께 분석하는 것이 중요

---

### 프로필 이미지 업데이트가 실시간으로 반영되지 않는 문제

#### 문제 상황
사용자가 프로필 이미지를 변경해도 네비게이션 바의 프로필 이미지가 즉시 업데이트되지 않음. 페이지를 새로고침해야만 변경된 이미지가 보이는 현상 발생.

#### 원인
**1. SecurityContext에 프로필 이미지 정보 부재**
- `CustomUserDetails`에 `profileImageUrl` 필드가 없어서 세션에 이미지 정보가 저장되지 않음
- 프로필 업데이트 후에도 `SecurityContext`가 갱신되지 않아 이전 정보가 유지됨

**2. 브라우저 캐시 문제**
- Supabase Storage에 같은 경로로 업로드하면 브라우저가 이전 이미지를 캐싱
- URL이 동일해서 브라우저가 새 이미지를 불러오지 않음

#### 해결 방법
**1. CustomUserDetails에 프로필 이미지 필드 추가**
```java
@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {
    private final String id;
    private final String email;
    private final String password;
    private final String profileImageUrl;  // 추가
    // ...
}
```

**2. 프로필 업데이트 시 SecurityContext 갱신**
```java
private void updateAuthentication(User user, UserProfile userProfile) {
    SocialLogin socialLogin = socialLoginRepository.findByUser(user).stream().findFirst()
        .orElseThrow(() -> new IllegalStateException("소셜 로그인 정보를 찾을 수 없습니다."));

    CustomUserDetails newUserDetails = CustomUserDetails.from(user, socialLogin, userProfile);

    Authentication newAuth = new UsernamePasswordAuthenticationToken(
        newUserDetails,
        null,
        newUserDetails.getAuthorities()
    );

    SecurityContextHolder.getContext().setAuthentication(newAuth);
    log.info("SecurityContext 업데이트 완료: userId={}", user.getUserId());
}
```

**3. 캐시 버스팅(Cache Busting) 적용**
```java
// Supabase 이미지 URL에 타임스탬프 쿼리 파라미터 추가
String cacheBustedUrl = imageUrl + "?t=" + System.currentTimeMillis();
```

**4. 프론트엔드에서 네비게이션 바 이미지 동적 업데이트**
```javascript
// 프로필 업데이트 후 네비게이션 바 이미지도 즉시 변경
const navProfileImg = document.querySelector('#profileButton img');
if (navProfileImg && updatedProfile.profileImageUrl) {
    navProfileImg.src = updatedProfile.profileImageUrl;
}
```

#### 결과
- 프로필 이미지 변경 시 네비게이션 바에 즉시 반영
- 페이지 새로고침 없이 실시간 업데이트
- 브라우저 캐시 문제 해결

#### 교훈
- Spring Security의 `SecurityContext`는 요청 처리 중에만 유지되므로, 사용자 정보 변경 시 명시적으로 갱신 필요
- 정적 파일 URL에는 캐시 버스팅 기법(타임스탬프, 버전 번호 등) 적용이 필수
- 프론트엔드와 백엔드의 상태 동기화를 위해서는 API 응답 후 즉시 UI 업데이트 필요

<br>

## 팀 협업 컨벤션

- [커밋 컨벤션](docs/COMMIT_CONVENTION.md)
- 이슈/PR 컨벤션
  - GitHub 템플릿 사용(.github/ISSUE_TEMPLATE, PULL_REQUEST_TEMPLATE.md)

<br>

## 팀원
| <img src="https://github.com/so-myoung.png" width="100" height="100" /> | <img src="https://github.com/jk-Nam.png" width="100" height="100" /> | <img src="https://github.com/jihun4452.png" width="100" height="100" /> | <img src="https://github.com/yerincho94.png" width="100" height="100" /> | <img src="https://github.com/c-wonjun.png" width="100" height="100" /> |
| :---: | :---: | :---: | :---: | :---: |
| 김소명 | 남준구 | 박지훈 | 조예린 | 최원준 |
| [so-myoung](https://github.com/so-myoung) | [jk-Nam](https://github.com/jk-Nam) | [jihun4452](https://github.com/jihun4452) | [yerincho94](https://github.com/yerincho94) | [c-wonjun](https://github.com/c-wonjun) |
