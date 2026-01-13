```
io/
└─ mcger/
   └─ core/
      ├─ CorePlugin.java          // 진입점
      │
      ├─ lifecycle/               // 서버/플러그인 생명주기
      │   ├─ CoreLifecycle.java
      │   └─ ServerState.java
      │
      ├─ runtime/                 // 현재 서버 런타임 정보
      │   ├─ RuntimeContext.java
      │   ├─ WorldRuntime.java
      │   └─ PlayerRuntime.java
      │
      ├─ messaging/               // “통신”의 중심
      │   ├─ MessageBus.java
      │   ├─ MessageType.java
      │   └─ envelope/
      │       ├─ CoreMessage.java
      │       └─ MessageHeader.java
      │
      ├─ bridge/                  // 외부/내부 연결 지점
      │   ├─ plugin/              // 다른 Paper 플러그인
      │   │   ├─ PluginBridge.java
      │   │   └─ PluginMessageListener.java
      │   │
      │   ├─ electron/            // Electron / Node 연계
      │   │   ├─ ElectronBridge.java
      │   │   └─ ControlChannel.java
      │   │
      │   └─ storage/             // SQLite / 영속 계층
      │       ├─ StorageBridge.java
      │       └─ SqliteProvider.java
      │
      ├─ command/                 // (필요 시) 관리자 명령
      │   └─ CoreCommand.java
      │
      └─ util/                    // 순수 유틸
          └─ JsonUtil.java

```
