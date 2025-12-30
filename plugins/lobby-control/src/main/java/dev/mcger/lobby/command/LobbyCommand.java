package dev.mcger.lobby.command;

import dev.mcger.lobby.editor.LobbyEditorManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    if (!(sender instanceof Player player)) {
      sender.sendMessage("Player only.");
      return true;
    }

    if (args.length == 0) {
      player.sendMessage("/lobby edit on|off");
      player.sendMessage("/lobby editor add <player>");
      player.sendMessage("/lobby editor remove <player>");
      return true;
    }

    // /lobby edit on|off
    if (args[0].equalsIgnoreCase("edit")) {
      LobbyEditorManager.toggleEditor(player.getUniqueId());
      player.sendMessage("Lobby edit mode toggled.");
      return true;
    }

    // /lobby editor add/remove <player>
    if (args[0].equalsIgnoreCase("editor") && args.length == 3) {
      var target = Bukkit.getPlayer(args[2]);
      if (target == null) {
        player.sendMessage("Player not found.");
        return true;
      }

      if (args[1].equalsIgnoreCase("add")) {
        LobbyEditorManager.addEditor(target.getUniqueId());
        player.sendMessage("Added editor: " + target.getName());
        return true;
      }

      if (args[1].equalsIgnoreCase("remove")) {
        LobbyEditorManager.removeEditor(target.getUniqueId());
        player.sendMessage("Removed editor: " + target.getName());
        return true;
      }
    }

    player.sendMessage("Invalid command.");
    return true;
  }
}
