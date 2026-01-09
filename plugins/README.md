## Mcger Platform – Core Plugin 기반 제어 구조 설계 문서

### 1. 개요 (Overview)

본 문서는 Mcger 플랫폼에서 사용되는
Core Plugin 중심 제어 아키텍처의 구조와 역할,
그리고 플러그인·외부 시스템 간 흐름을 설명한다.

이 설계의 목표는 다음과 같다.

- Paper 서버 내 여러 플러그인을 중앙 기준(Core) 으로 통합
- 로직과 표현(UI)을 분리하여 확장 가능성 확보
- Electron / Node.js 등 외부 컨트롤 플레인과의 자연스러운 연결
- “기능이 늘어나도 구조가 무너지지 않는” 아키텍처 유지

---

### 2. 설계 철학 (Design Principles)

#### 2.1 Core는 “기능 플러그인”이 아니다

Core Plugin은 다음을 하지 않는다:

- 월드 생성 / 삭제
- UI 처리
- 명령어 제공
- 플레이어 행동 제어
- DB 비즈니스 로직

Core Plugin의 역할은 단 하나다.

> “서버 전체의 기준점(Central Authority)이 된다.”

---

### 2.2 책임 분리 원칙

| 계층           | 책임                  |
| -------------- | --------------------- |
| Core Plugin    | 상태, 기준, 흐름 정의 |
| Lobby Plugin   | 표현(UI), 상호작용    |
| World Plugin   | 개별 월드 로직        |
| Electron App   | 시각화, 운영 제어     |
| Node / Storage | 실제 인프라 처리      |

Core는 결정하지 않고,
다른 계층은 Core를 기준으로 행동한다.

---

### 3. 전체 구조 개요

```
────────────────────────────
       Electron App
  (시각화 / 운영 / 제어)
─────────────▲──────────────
             │
     (메시지 / 이벤트)
             │
─────────────┴──────────────
        Core Plugin

  - Runtime 상태
  - Lifecycle
  - Messaging 정의
  - Bridge (연결 통로)
─────────────▲──────────────
             │
     PluginBridge (조회)
             │
─────────────┴──────────────
    Lobby / World Plugin
  - UI
  - NPC / 포탈
  - 플레이어 상호작용
────────────────────────────
```

---

### 4. Core Plugin 내부 구조

#### 4.1 패키지 구조

```
io.mcger.core
├─ runtime/        // 현재 서버 상태 보관
├─ lifecycle/     // 상태 변화 표현
├─ messaging/     // 메시지 타입 정의
│  └─ envelope/
├─ bridge/        // 연결 통로
│  ├─ plugin/
│  ├─ electron/
│  └─ storage/
└─ util/
```

---

### 5. Runtime 계층

#### 5.1 목적

Runtime은 “지금 서버가 어떤 상태인가” 를 표현한다.

#### 5.2 구성 요소

- ServerState (enum)
- RuntimeContext

#### 5.3 특징

- 값 보관 전용
- 판단 로직 없음
- 정책 없음

```
ServerState.STARTING
ServerState.READY
ServerState.IDLE
ServerState.SHUTTING_DOWN
```

Runtime은 읽기/쓰기 가능한 상태 컨테이너일 뿐이다.

---

### 6. Lifecycle 계층

#### 6.1 목적

Lifecycle은 상태 변화 자체를 이벤트로 표현한다.

#### 6.2 구성 요소

- CoreLifecycle
- ServerLifecycleEvent

#### 6.3 특징

- “변경 가능 여부”를 판단하지 않음
- 상태 전이 시 이벤트 객체만 생성
- 실제 처리/결정은 외부 책임

```
STARTING → READY → IDLE → SHUTTING_DOWN
```

---

### 7. Messaging 계층

#### 7.1 목적

Messaging은 Core가 이해하는 ‘언어’를 정의한다.

#### 7.2 구성 요소

- MessageType
- CoreMessage (Envelope)

#### 7.3 특징

- 처리 로직 없음
- switch-case 없음
- 직렬화 방식 미정 (의도적)

Messaging은
`Plugin ↔ Core ↔ Electron 간 공통 언어` 역할을 한다.

---

### 8. Bridge 계층 (핵심)

Bridge는 계층 간 연결 지점이다.

#### 8.1 PluginBridge

- 다른 Paper 플러그인이 Core에 접근하는 유일한 통로
- Core의 내부 구현을 숨김
- 현재는 조회 중심 API

```
RuntimeContext runtime = PluginBridge.runtime();
```

#### 8.2 Electron Bridge

- 외부 컨트롤 플레인 입력 수신
- WebSocket 구현은 추후
- 현재는 개념적 Stub

#### 8.3 Storage Bridge

- DB 존재 사실만 표현
- 쿼리 / 스키마 없음
- Core는 저장 방식에 관여하지 않음

---

### 9. 플러그인 간 흐름 예시 (Lobby 기준)

#### 9.1 서버 시작

```scss
Paper Server Start
   ↓
Core Plugin onEnable()
   ↓
RuntimeContext 초기화
   ↓
PluginBridge 초기화
```

#### 9.2 로비 플러그인 로드

```csharp
Lobby Plugin onEnable()
   ↓
PluginBridge.runtime() 조회
   ↓
Core 상태 확인
   ↓
이벤트 등록 / UI 활성화

```

---

### 10. Electron 연계 시 확장 흐름 (예정)

향후 Electron App에서는:

- Bridge 단계를 색상으로 시각화
- requestId 기준 흐름 추적
- “어디서 막혔는지” 즉시 확인

예:

```
🟦 PLUGIN_IN
🟨 CORE_LIFECYCLE
🟩 MESSAGE_CREATED
🟥 ELECTRON_OUT
```

이를 위해 Core 내부에는
BridgeTrace 개념을 확장 가능하게 설계해 두었다.

---

### 11. 이 구조의 핵심 장점

- Core가 비대해지지 않음
- 로비/월드 플러그인이 단순해짐
- 외부 시스템(Electron) 연계가 자연스러움
- 테스트 플러그인으로 구조 검증 가능
- “운영 가능한 플랫폼”으로 확장 가능

### 12. 결론

이 Core Plugin 설계는:

> “플러그인을 만드는 설계”가 아니라
> “플러그인 기반 시스템을 만드는 설계”다.

이 구조가 유지되는 한,
기능이 늘어나도 중앙 제어 흐름은 흔들리지 않는다.
