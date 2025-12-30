package dev.mcger.protocol.world;

public enum WorldType {
  SURVIVAL, // 일반 생존 월드
  CREATIVE, // 건축 중심
  ADVENTURE, // 맵 플레이용
  MINIGAME, // 미니게임
  LOBBY, // 로비 전용
  CUSTOM; // 커스텀 규칙

  /**
   * 로비에서 생성 가능한 타입인지
   */
  public boolean isCreatableFromLobby() {
    return this != LOBBY;
  }
}
