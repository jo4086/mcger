package io.mcger.navigator;

import io.mcger.core.bridge.plugin.PluginBridge;
import io.mcger.core.runtime.RuntimeContext;
import io.mcger.navigator.command.WorldsCommand;
import io.mcger.navigator.listener.MainMenuListener;
import io.mcger.navigator.listener.WorldListMenuListener;
import io.mcger.navigator.ui.MainMenu;
import io.mcger.navigator.ui.PlaceholderMenu;
import io.mcger.navigator.ui.WorldListMenu;
import org.bukkit.plugin.java.JavaPlugin;

public class McgerNavigatorPlugin extends JavaPlugin {

  private MainMenu mainMenu;
  private WorldListMenu worldListMenu;
  private PlaceholderMenu placeholderMenu;

  @Override
  public void onEnable() {
    RuntimeContext runtime = PluginBridge.runtime();
    if (runtime == null) {
      getLogger().severe("Core runtime found. Navigator disabled.");
      getServer().getPluginManager().disablePlugin(this);
      return;
    }

    mainMenu = new MainMenu();
    worldListMenu = new WorldListMenu();
    placeholderMenu = new PlaceholderMenu();

    getServer().getPluginManager().registerEvents(
        new WorldListMenuListener(worldListMenu),
        this);

    getServer().getPluginManager().registerEvents(
        new MainMenuListener(worldListMenu, placeholderMenu),
        this);

    if (getCommand("worlds") != null) {
      getCommand("worlds").setExecutor(new WorldsCommand(mainMenu));
    }

    getLogger().info("Navigator enabled. Core state  =  " + runtime.getServerState());
  }
}
