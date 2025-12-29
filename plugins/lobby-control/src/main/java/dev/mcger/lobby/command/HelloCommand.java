package dev.mcger.lobby.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelloCommand implements CommandExecutor {

  @Override
  public boolean onCommand(
      CommandSender sender,
      Command command,
      String label,
      String[] args) {
    if (sender instanceof Player player) {
      player.sendMessage("Hello from LobbyControl plugin!");
    } else {
      sender.sendMessage("This command is player-only.");
    }
    return true;
  }
}
