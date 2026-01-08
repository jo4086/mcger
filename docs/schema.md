# 메인 데이터베이스 스키마 설계 문서

### 1. 개요

본 문서는 월드 기반 개인 서버 서비스의 핵심 데이터 구조를 정의한다.
본 서비스는 서버 개념을 유저에게 노출하지 않고,
유저 → 월드 → 친구 접근 권한을 중심으로 동작한다.

데이터베이스는 다음 원칙을 따른다.

- 유저는 루트 엔티티이다.
- 월드는 유저가 소유하는 리소스이다.
- 친구는 유저 간 관계 엔티티이다.
- 서버 상태 및 실시간 정보는 DB가 아닌 런타임에서 관리한다.

---

### 2. User 테이블

#### 2.1 역할

- 서비스 내 유저의 단일 진입점 엔티티
- 모든 월드 및 관계 데이터의 기준점
- 인증 및 소유권 판단의 기준

#### 2.2 스키마 정의

```sql
User
```

| 컬럼명      | 타입          | 설명                                     |
| ----------- | ------------- | ---------------------------------------- |
| id          | PK            | 내부 고유 식별자                         |
| uuid        | UUID (Unique) | 마인크래프트 유저 고유 UUID              |
| username    | String        | 마인크래프트 유저네임                    |
| worldCount  | Integer       | 유저가 보유한 월드 수 (캐시값)           |
| friendCount | Integer       | 친구 수 (캐시값)                         |
| status      | Enum          | 유저 상태 (ACTIVE / SUSPENDED / DELETED) |
| createdAt   | Timestamp     | 계정 생성 시각                           |
| updatedAt   | Timestamp     | 마지막 갱신 시각                         |

#### 2.3 설계 의도

- uuid를 기준 키로 사용하여 유저네임 변경에 안전
- worldCount, friendCount는 관계 테이블의 집계 캐시
- status를 통해 차단/정지/탈퇴 상태를 단일 컬럼으로 관리

---

### 3. World 테이블

#### 3.1 역할

- 유저가 생성한 월드 인스턴스의 메타데이터
- 서버 인스턴스 생성 및 접근 제어 판단의 기준

#### 3.2 스키마 정의

```sql
World
```

| 컬럼명           | 타입                 | 설명                                           |
| ---------------- | -------------------- | ---------------------------------------------- |
| id               | PK                   | 월드 고유 식별자                               |
| ownerUuid        | UUID                 | 월드 소유 유저 UUID (User.uuid 참조)           |
| worldName        | String               | 유저가 지정한 월드 이름                        |
| worldSlug        | String (Unique)      | 외부 식별용 월드 코드                          |
| worldType        | Enum                 | 월드 타입 (SURVIVAL / FLAT / VOID 등)          |
| status           | Enum                 | 월드 상태 (CREATED / ACTIVE / IDLE / ARCHIVED) |
| whitelistEnabled | Boolean              | 화이트리스트 사용 여부                         |
| passwordEnabled  | Boolean              | 비밀번호 사용 여부                             |
| isPrivate        | Boolean              | 비공개 월드 여부                               |
| lastAccessedAt   | Timestamp            | 마지막 접속 시각                               |
| createdAt        | Timestamp            | 월드 생성 시각                                 |
| updatedAt        | Timestamp            | 월드 정보 변경 시각                            |
| deletedAt        | Timestamp (Nullable) | 소프트 삭제 시각                               |

#### 3.3 설계 의도

- 월드는 서버가 아니라 서비스 단위로 취급
- worldSlug는 초대, 공유, 내부 라우팅용 식별자
- status는 서버 자동 ON/OFF 판단 기준으로 사용
- deletedAt을 통한 soft delete로 데이터 복구 가능성 유지

---

### 4. Friend 테이블

#### 4.1 역할

- 유저 간 친구 관계 및 상태 관리
- 월드 접근 권한 확장의 기반

#### 4.2 스키마 정의

```sql
Friend
```

| 컬럼명     | 타입      | 설명                                     |
| ---------- | --------- | ---------------------------------------- |
| id         | PK        | 관계 고유 식별자                         |
| userUuid   | UUID      | 기준 유저 UUID                           |
| friendUuid | UUID      | 친구 유저 UUID                           |
| status     | Enum      | 관계 상태 (PENDING / ACCEPTED / BLOCKED) |
| createdAt  | Timestamp | 관계 생성 시각                           |

#### 4.3 설계 의도

- 단방향 레코드 기반 관계
- 친구 요청 / 수락 / 차단 상태를 단일 테이블로 관리
- 월드 접근 권한 판단 시 참조 가능

---

### 5. WorldMember 테이블

#### 5.1 역할

- 월드와 유저 간의 접근 권한 및 관계 상태를 관리한다.
- 화이트리스트, 밴리스트, 매니저 권한을 단일 구조로 표현한다.
- 월드 입장 가능 여부 및 관리 권한 판단의 기준이 된다.

본 테이블은 **월드 소유권(User–World)** 과는 별도로,
월드에 참여하는 모든 유저의 상태를 표현한다.

#### 5.2 스키마 정의

```sql
WorldMember
```

| 컬럼명    | 타입                 | 설명                                    |
| --------- | -------------------- | --------------------------------------- |
| id        | PK                   | 관계 고유 식별자                        |
| worldId   | FK                   | 대상 월드 ID (World.id 참조)            |
| userUuid  | UUID                 | 대상 유저 UUID (User.uuid 참조)         |
| role      | Enum                 | 월드 내 역할 (OWNER / MANAGER / MEMBER) |
| roleLevel | Integer              | 관리자 등급 (선택, 숫자 기반)           |
| status    | Enum                 | 접근 상태 (ACTIVE / PENDING / BANNED)   |
| addedBy   | UUID                 | 해당 관계를 추가한 유저 UUID            |
| createdAt | Timestamp            | 관계 생성 시각                          |
| updatedAt | Timestamp            | 관계 변경 시각                          |
| expiresAt | Timestamp (Nullable) | 임시 권한 만료 시각                     |

#### 5.3 Enum 정의

##### 5.3.1 role (월드 내 역할)

| 값      | 설명                                 |
| ------- | ------------------------------------ |
| OWNER   | 월드 소유자 (World.ownerUuid와 동일) |
| MANAGER | 월드 관리자                          |
| MEMBER  | 일반 접근 허용 유저                  |

##### 5.3.2 status (관계 상태)

| 값      | 설명           |
| ------- | -------------- |
| ACTIVE  | 정상 접근 가능 |
| PENDING | 초대 대기 상태 |
| BANNED  | 월드 접근 차단 |

이를 통해 다음 개념을 단일 테이블로 표현한다.

- 화이트리스트 → status = ACTIVE
- 밴리스트 → status = BANNED
- 초대 대기 → status = PENDING

#### 5.4 설계 의도

- 월드–유저 관계는 N:M 구조이므로 별도 테이블로 분리
- 화이트리스트 / 밴리스트 / 매니저 리스트를 하나의 모델로 통합
- 날짜 기반 권한, 임시 밴, 관리자 등급 확장이 가능
- JSON/배열 컬럼 없이 쿼리 가능한 구조 유지

#### 5.5 Friend 테이블과의 역할 분리

| 구분      | Friend      | WorldMember    |
| --------- | ----------- | -------------- |
| 관계 범위 | 유저 ↔ 유저 | 월드 ↔ 유저    |
| 목적      | 사회적 관계 | 공간 접근 권한 |
| 차단 의미 | 개인 차단   | 월드 출입 차단 |
| 사용 시점 | 친구 기능   | 월드 입장/관리 |

두 테이블은 의미적으로도, 기술적으로도 분리된 책임을 가진다.

---

### 6. 데이터 관계 요약

```scss
User (1)
 ├─ owns → World (N)
 └─ relates → Friend (N)
               └─ User (1)
```

- User → World : 1:N 소유 관계
- User ↔ User : Friend 테이블을 통한 관계 모델링

---

### 7. 설계 범위 외 항목 (의도적 제외)

다음 정보는 DB에 저장하지 않는다.

- 서버 IP / 포트
- 서버 프로세스 상태
- 실시간 접속자 수
- TPS, 메모리 사용량

위 정보는 런타임 상태로 관리하며,
DB는 영속 메타데이터만 책임진다.

---

### 8. 현재 설계 상태 요약

- 핵심 엔티티 정의 완료
- 관계 모델 명확
- 서버/월드 자동화 구조와 충돌 없음
- 확장(권한, 로그, 통계)에 안전한 기반 확보

### 9. 다음 확장 시 추가 가능한 테이블 (참고)

- WorldAccess (월드별 접근 권한 세분화)
- ServerInstance (서버 상태 스냅샷)
- AuditLog (관리자/시스템 조작 로그)
