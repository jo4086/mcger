package io.mcger.core.runtime;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

/**
 * Core 서버의 런타임 상태 컨텍스트
 *
 * 역할:
 * - 현재 서버 상태 보관
 * - Bukkit Server 참조 보관
 *
 * ❌ 하지 않는 것:
 * - 상태 판단
 * - 상태 전이 결정
 * - 외부 시스템 호출
 */
public class RuntimeContext {

  private final Plugin plugin;
  private final Server server;

  private ServerState serverState;

  public RuntimeContext(Plugin plugin) {
    this.plugin = plugin;
    this.server = plugin.getServer();
    this.serverState = ServerState.STARTING;
  }

  /*
   * =========================
   * Getter / Setter
   * =========================
   */
  public ServerState getServerState() {
    return serverState;
  }

  public void setServerState(ServerState serverState) {
    this.serverState = serverState;
  }

  public Plugin getPlugin() {
    return plugin;
  }

  public Server getServer() {
    return server;
  }
}
