package dev.mcger.lobby;

// import org.bukkit.plugin.java.JavaPlugin;

//
// public class LobbyPlugin extends JavaPlugin {
//
//     @Override
//     public void onEnable() {
//         getLogger().info("LobbyControl plugin enabled");
//     }
//
//     @Override
//     public void onDisable() {
//         getLogger().info("LobbyControl plugin disabled");
//     }
// }

import dev.mcger.lobby.command.HelloCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    getLogger().info("LobbyControl plugin enabled");

    getCommand("hello").setExecutor(new HelloCommand());
  }

  @Override
  public void onDisable() {
    getLogger().info("LobbyControl plugin disabled");
  }
}
