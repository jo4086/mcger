package dev.mcger.lobby;

import dev.mcger.lobby.command.HelloCommand;
import dev.mcger.lobby.command.LobbyCommand;
import dev.mcger.lobby.listener.LobbyProtectionListener;

import org.bukkit.plugin.java.JavaPlugin;

public class LobbyPlugin extends JavaPlugin {

  // 전역 접근 인스턴스
  private static LobbyPlugin instance;

  @Override
  public void onEnable() {
    // 인스턴스 초기화
    instance = this;

    getLogger().info("Lobby plugin enabled");

    getServer().getPluginManager().registerEvents(
        new LobbyProtectionListener(),
        this);

    getLogger().info("LobbyProtectionListener registered");

    // Command 등록
    getCommand("hello").setExecutor(new HelloCommand());
    getCommand("lobby").setExecutor(new LobbyCommand());
    getLogger().info("Command registered");
  }

  @Override
  public void onDisable() {
    // 정리
    instance = null;
    getLogger().info("Lobby plugin disabled");
  }

  // 어디서든 플러그인 인스턴스 접근 가능
  public static LobbyPlugin getInstance() {
    return instance;
  }
}
