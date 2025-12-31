package io.mcger.core.runtime;

/**
 * 서버의 현재 생명주기 상태
 *
 * ⚠️ 주의:
 * - 판단 로직 없음
 * - 상태 전이 로직 없음
 * - 값(enum) 정의만 담당
 */
public enum ServerState {

  /**
   * 서버가 막 시작되는 중
   */
  STARTING,

  /**
   * 외부 요청을 받을 수 있는 상태
   */
  READY,

  /**
   * 플레이어가 없고 유휴 상태
   */
  IDLE,

  /**
   * 종료 절차 진행 중
   */
  SHUTTING_DOWN
}
