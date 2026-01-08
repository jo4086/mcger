package dev.mcger.protocol.world;

public enum WorldStatus {
  CREATED, // 생성 직후
  ACTIVE, // 활성 상태 (접속 가능)
  IDLE, // 유휴 상태 (접속자 없음)
  ARCHIVED, // 보관 상태 (비활성)
  DELETED; // 삭제됨 (논리적)

  /**
   * 로비에서 변경 요청 가능한 상태인지
   */
  public boolean isUpdatableFromLobby() {
    return this != DELETED;
  }
}
