# 2025-12-31 00:02

> [!NOTE] 플러그인 빌드 오류로 인한 통신 구조 변경 결정 기록

<details>
<summary>...</summary>

### 영향 범위

- lobby-control (Paper)
- velocity-bridge
- node-world-server

---

### 문제점 (플러그인 빌드 에러)

- Paper 플러그인에서 WebSocket 통신을 직접 구현하려고 시도함
- `Java-WebSocket` 라이브러리를 사용하기 위해 ShadowJar 적용
- Gradle 8.x + Java 21 + Shadow plugin 환경에서  
  `META-INF` 병합 과정 중 빌드 오류 발생
- 설정(exclude, mergeServiceFiles, duplicateStrategy)으로 해결 불가
- 결과적으로 **플러그인 빌드 자체가 불안정한 상태**가 됨

---

### 해결방법

- Paper 플러그인에서 WebSocket 관련 로직 전부 제거
- 외부 네트워크 통신 책임을 **Velocity + Node.js**로 분리
- 최종 통신 구조 변경:
  - Paper ↔ Velocity : Plugin Messaging
  - Velocity ↔ Node : WebSocket
- Shadow 플러그인 및 외부 네트워크 라이브러리 의존성 제거
- Paper 플러그인은 “요청 생성 + 메시지 전달” 역할만 유지

---

### 차후 개발 방향

- Velocity 플러그인에서 Node.js WebSocket 서버와의 브릿지 구현
- Node.js 서버에서 월드 생성 / 상태 관리 / DB 처리 담당
- 메시지 타입(`MessageType`)을 공용 프로토콜로 유지
- Result 메시지를 Velocity → Paper로 다시 전달하여 UX 처리
- Paper 플러그인은 최대한 순수하게 유지하여:
  - 빌드 안정성 확보
  - 서버 업데이트 대응 용이
  - 기능 확장 시 구조 변경 최소화

</details>
