package io.mcger.core.lifecycle;

import io.mcger.core.runtime.ServerState;

/**
 * 서버 생명주기 이벤트
 *
 * 의미:
 * - 서버 상태가 "바뀌었다"는 사실만 표현
 * - 왜 바뀌었는지는 알지 않음
 */
public class ServerLifecycleEvent {

  private final ServerState previousState;
  private final ServerState currentState;

  public ServerLifecycleEvent(ServerState previousState, ServerState currentState) {
    this.previousState = previousState;
    this.currentState = currentState;
  }

  public ServerState getPreviousState() {
    return previousState;
  }

  public ServerState getCurrentState() {
    return currentState;
  }
}
