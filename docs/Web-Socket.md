# Web Socket

```
[ Minecraft Lobby Server ]
        │
        │  WebSocket (persistent)
        │
[ Control Server (Electron main or daemon) ]
        │
        ├── Prisma (SQLite)
        ├── World Provisioner
        └── Server Runtime Manager
```

**서버 내부 처리 흐름**

```
REQUEST
  ↓
VALIDATE (슬롯 / 중복 / 이름)
  ↓
DB_WRITE
  ↓
FS_PROVISION
  ↓
READY

```

### Message Model (이벤트 스트림)

```json
// COMMON_MESSAGE_FORMAT
{
  "type": "EVENT_TYPE",
  "requestId": "uuid",
  "payload": {}
}

// WORLD_CREATE_REQUEST
{
  "type": "EVENT_TYPE",
  "requistId": "uuid",
  "payload": {
    "userUUid": "user-uuid",
    "worldName": "myWorld",
    "worldType": "SURVIVAL"
  }
}

//WORLD_CREATE_ACCEPTED
{
  "type": "WORLD_CREATE_ACCEPTED",
  "requestId": "req-123",
  "payload": {
    "message": "월드 생성 요청을 처리 중입니다"
  }
}

// WORLD_CREATE_PROGRESS
{
  "type": "WORLD_CREATE_PROGRESS",
  "requestId": "req-123",
  "payload": {
    "step": "PROVISIONING"
  }
}

// WORLD_CREATE_DONE
{
  "type": "WORLD_CREATE_DONE",
  "requestId": "req-123",
  "payload": {
    "worldId": "world-id",
    "worldSlug": "myworld-abc"
  }
}

// WORLD_CREATE_FAILED
{
  "type": "WORLD_CREATE_FAILED",
  "requestId": "req-123",
  "payload": {
    "reason": "WORLD_LIMIT_EXCEEDED"
  }
}
```
