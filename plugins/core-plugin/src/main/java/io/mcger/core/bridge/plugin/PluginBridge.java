package io.mcger.core.bridge.plugin;

import io.mcger.core.runtime.RuntimeContext;

/**
 * 다른 Paper 플러그인이 Core에 접근하는 단일 진입점
 *
 * ❌ 로직 없음
 * ❌ 판단 없음
 */
public class PluginBridge {

  private static RuntimeContext runtimeContext;

  private PluginBridge() {
  }

  public static void init(RuntimeContext context) {
    runtimeContext = context;
  }

  public static RuntimeContext runtime() {
    return runtimeContext;
  }
}
