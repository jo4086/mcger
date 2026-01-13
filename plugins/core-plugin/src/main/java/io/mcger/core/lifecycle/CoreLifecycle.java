package io.mcger.core.lifecycle;

import io.mcger.core.runtime.RuntimeContext;
import io.mcger.core.runtime.ServerState;

/**
 * 서버 생명주기 관리자
 *
 * 역할:
 * - RuntimeContext의 상태를 변경
 * - 상태 변경 시 이벤트 객체 생성
 *
 * ❌ 판단 로직 없음
 * ❌ 외부 시스템 호출 없음
 */
public class CoreLifecycle {

  private final RuntimeContext runtimeContext;

  public CoreLifecycle(RuntimeContext runtimeContext) {
    this.runtimeContext = runtimeContext;
  }

  /**
   * 서버 상태 변경
   *
   * @param newState 변경할 상태
   * @return 상태 변경 이벤트
   */
  public ServerLifecycleEvent changeState(ServerState newState) {
    ServerState previous = runtimeContext.getServerState();
    runtimeContext.setServerState(newState);
    return new ServerLifecycleEvent(previous, newState);
  }
}
