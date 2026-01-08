package io.mcger.core.messaging;

/**
 * Core 플러그인이 인식하는 메시지 타입
 *
 * ⚠️ 주의:
 * - "무엇을 한다"가 아니라
 * - "무슨 종류의 메시지인가"만 표현
 */
public enum MessageType {

  /*
   * =========================
   * Server lifecycle
   * =========================
   */
  SERVER_STARTED,
  SERVER_READY,
  SERVER_IDLE,
  SERVER_SHUTTING_DOWN,

  /*
   * =========================
   * World (요청/이벤트)
   * =========================
   */
  WORLD_CREATE_REQUEST,
  WORLD_CREATE_ACCEPTED,
  WORLD_CREATE_FAILED,

  WORLD_JOIN_REQUEST,
  WORLD_LEAVE_EVENT,

  /*
   * =========================
   * Plugin / Control
   * =========================
   */
  PLUGIN_HEARTBEAT,
  CONTROL_PING
}
