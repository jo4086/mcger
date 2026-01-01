package io.mcger.lobby;

import org.bukkit.plugin.java.JavaPlugin;

import io.mcger.core.bridge.plugin.PluginBridge;
import io.mcger.core.runtime.RuntimeContext;
import io.mcger.lobby.listener.BlockProtectListener;
import io.mcger.lobby.listener.PlayerJoinListener;

public class McgerLobbyPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    RuntimeContext runtime = PluginBridge.runtime();

    if (runtime == null) {
      getLogger().severe("Core runtime found. Lobby disabled.");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    getServer().getPluginManager().registerEvents(
        new PlayerJoinListener(),
        this);

    getServer().getPluginManager().registerEvents(
        new BlockProtectListener(),
        this);

    getLogger().info("Lobby enabled. Core state = " + runtime.getServerState());
  }
}
