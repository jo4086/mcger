# #1 2026-01-01 20:03

# Repository Overview (plugins)

이 문서는 `plugin-architecture.md`에 맞춘 전체 흐름을 기준으로,
현재 디렉토리 구조와 각 구성요소의 역할을 요약한다.

## 1) 전체 흐름 (plugin-architecture.md 기준)

- 사용자는 고정 도메인으로 접속한다.
- 로비 월드로 이동하며, 로비에서 NPC/포탈 등 상호작용을 한다.
- 로비는 보호되고 제한된 공간이며, 관리자만 수정 가능하다.
- 로비 UI에서 월드 목록/페이징, 내 월드(최대 3개) 진입/생성을 관리한다.
- 월드 생성 시 비밀번호, 월드명, 타입(고정), 화이트리스트, 최대 인원 등을 설정한다.
- 사용자별 인스턴스 서버를 생성하며, 접속자가 없으면 서버는 종료된다.
- 내 월드에서 설정 UI로 월드명/비밀번호/화이트리스트 등을 변경한다.
- 친구/친구 월드 이동, 비밀번호/화이트리스트 검증 흐름이 있다.
- 플러그인 추가 UI로 서버 재시작 후 플러그인을 적용한다.
- Electron 앱은 전체 흐름을 시각화하고 WebSocket으로 연동한다.

```
[ Lobby Plugin ]        [ World Server Plugins ]
        |                         |
        +---- Plugin Message -----+
                     |
          [ Central Control Plugin ]
                     |
        +------------+------------+
        |                         |
   [ Node.js Control Plane ]   [ Electron ]
```

## 2) 최상위 구조 요약

- `core-plugin/`
  - 중앙 제어(Core) 플러그인. Runtime/Lifecycle/Messaging/Bridge 개념을 담는 핵심 모듈.
- `lobby-plugin/`
  - 로비 월드 UI/상호작용 및 보호 로직 담당.
- `gradle/`, `gradlew`, `build.gradle`, `settings.gradle`
  - Gradle 빌드 설정과 래퍼.
- `checkstyle.xml`
  - 코드 스타일 규칙.
- `plugin-architecture.md`
  - 전체 서비스 흐름 정의 문서.
- `README.md`
  - Core 중심 아키텍처 설계 문서.
- `package.json`
  - JS 기반 툴링/스크립트용 메타데이터로 보이며, 별도 확인 필요.
- `build/`, `.gradle/` 및 각 하위 `build/`/`.gradle/`
  - 빌드 산출물 및 캐시(자동 생성).

## 3) core-plugin 요약

현재 존재하는 핵심 코드:

- `core-plugin/src/main/java/io/mcger/core/McgerCorePlugin.java`
  - Core 플러그인 진입점.
- `core-plugin/src/main/java/io/mcger/core/runtime/`
  - `RuntimeContext`, `ServerState`: 서버 상태 표현.
- `core-plugin/src/main/java/io/mcger/core/lifecycle/`
  - `CoreLifecycle`, `ServerLifecycleEvent`: 상태 변화 이벤트.
- `core-plugin/src/main/java/io/mcger/core/messaging/`
  - `MessageType`, `CoreMessage`: 공통 메시지 모델.
- `core-plugin/src/main/java/io/mcger/core/bridge/`
  - `plugin/PluginBridge`: 타 플러그인이 Core에 접근하는 통로.
  - `electron/ControlChannel`, `ControlEventType`: 외부 컨트롤 플레인 연계.
  - `storage/StorageBridge`: 저장 계층의 존재만 표현.
- `core-plugin/structure.md`
  - Core 플러그인의 의도된 전체 패키지 구조(현재 구현과 차이가 있을 수 있음).

## 4) lobby-plugin 요약

- `lobby-plugin/src/main/java/io/mcger/lobby/McgerLobbyPlugin.java`
  - 로비 플러그인 진입점.
- `lobby-plugin/src/main/java/io/mcger/lobby/listener/`
  - `PlayerJoinListener`: 접속 흐름.
  - `BlockProtectListener`: 로비 보호.
- `lobby-plugin/src/main/java/io/mcger/lobby/service/LobbySpawnService.java`
  - 스폰 위치 관리 등 로비 서비스.

## 5) 아키텍처 정합성 포인트

- Core는 상태/메시지/브리지 정의에 집중하고, 로비/월드는 표현 및 상호작용을 담당한다.
- Lobby는 PluginBridge를 통해 Core의 상태를 조회하고 로비 UI/보호 정책을 적용한다.
- Electron/Node 컨트롤 플레인은 Core의 Bridge를 통해 이벤트/상태를 교환한다.

# #2 2025-09-15

### 1) 추가 플러그인 구성(정리)

- Core: 상태/메시지/브리지, UUID 매핑, WebSocket/DB 연결 지점
- Lobby: 로비 규칙/보호, UI(NPC/포탈), 월드 탐색/생성 진입
- World: 월드 생성/설정 변경, 비밀번호/화이트리스트, 권한 체크
- Friends(선택): 친구 목록/이동/온라인 상태
- BlockSystem(선택): 블록 스킨/가짜 블록, 캐싱, 소유권/허용자 권한
- Plugin-Manager(선택): 플러그인 추가 UI, 서버 재시작/적용 흐름

### 2) 권한 모델 공통화

- 블록 소유권/허용자 모델은 월드/포탈/기능블록 등에서도 재사용 가능하다.
- Core에 공통 권한 모델(Owner + Allowlist) 정의를 두고, 각 플러그인은 이를 적용한다.
- 영속 저장은 Storage 브리지를 통해 공통 스키마로 관리한다.

### 3) 외부 시스템 역할 정리

- WebSocket: Core가 외부(웹/일렉트론)와 실시간 상태/이벤트를 동기화한다.
- SQLite: 월드 설정, 친구/UUID 매핑, 권한 정보 등 영속 데이터를 저장한다.
