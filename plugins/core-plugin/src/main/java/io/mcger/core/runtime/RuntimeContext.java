package io.mcger.core.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
  private final List<WorldSummary> worldSummaries;

  public RuntimeContext(Plugin plugin) {
    this.plugin = plugin;
    this.server = plugin.getServer();
    this.serverState = ServerState.STARTING;
    this.worldSummaries = new ArrayList<>();
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

  public List<WorldSummary> getWorldSummaries() {
    return Collections.unmodifiableList(worldSummaries);
  }

  public void registerWorldSummary(WorldSummary summary) {
    worldSummaries.add(summary);
  }

  public void clearWorldSummaries() {
    worldSummaries.clear();
  }
}
