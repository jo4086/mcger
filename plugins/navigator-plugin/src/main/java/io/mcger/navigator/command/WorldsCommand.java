package io.mcger.navigator.command;

import io.mcger.core.bridge.plugin.PluginBridge;
import io.mcger.core.runtime.RuntimeContext;
import io.mcger.navigator.ui.MainMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldsCommand implements CommandExecutor {

  private final MainMenu menu;

  public WorldsCommand(MainMenu menu) {
    this.menu = menu;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(Component.text("Player only command.", NamedTextColor.RED));
    }

    RuntimeContext runtime = PluginBridge.runtime();
    if (runtime == null) {
      sender.sendMessage(Component.text("Core runtime n ot ready.", NamedTextColor.RED));
    }

    menu.open((Player) sender);
    return true;
  }
}
