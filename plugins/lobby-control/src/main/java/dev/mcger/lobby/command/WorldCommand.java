package dev.mcger.lobby.command;

import java.util.Map;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mcger.lobby.world.service.LobbyWorldActionService;
import dev.mcger.lobby.world.service.LobbyWorldQueryService;
import dev.mcger.lobby.world.service.LobbyWorldSearchService;
import dev.mcger.protocol.world.WorldConfigKey;
import dev.mcger.protocol.world.WorldType;

public class WorldCommand implements CommandExecutor {
  private final LobbyWorldActionService actionService;
  private final LobbyWorldQueryService queryService;
  private final LobbyWorldSearchService searchService;

  public WorldCommand(
      LobbyWorldActionService actionService,
      LobbyWorldQueryService queryService,
      LobbyWorldSearchService searchService) {
    this.actionService = actionService;
    this.queryService = queryService;
    this.searchService = searchService;
  }

  @Override
  public boolean onCommand(
      CommandSender sender,
      Command command,
      String label,
      String[] args) {
    if (!(sender instanceof Player player)) {
      sender.sendMessage("Player only.");
      return true;
    }

    UUID uuid = player.getUniqueId();

    if (args.length == 0) {
      player.sendMessage("/world list");
      player.sendMessage("/world create <name>");
      player.sendMessage("/world join <name>");
      player.sendMessage("/world search <keyword>");
      return true;
    }

    switch (args[0].toLowerCase()) {
      case "list" -> {
        queryService.requestMyWorldList(uuid);
      }

      case "create" -> {
        if (args.length < 2) {
          player.sendMessage("Usage: /world create <name>");
          return true;
        }
        actionService.requestCreateWorld(
            uuid,
            args[1],
            WorldType.SURVIVAL // 기본값
        );
      }

      case "join" -> {
        if (args.length < 2) {
          player.sendMessage("Usage: /world join <slug>");
          return true;
        }
        actionService.requestJoinWorld(uuid, args[1]);

      }
      case "search" -> {
        if (args.length < 2) {
          player.sendMessage("Usage: /world search <keyword>");
          return true;
        }
        searchService.requestSearchWorlds(uuid, args[1], 0, 10);
      }

      case "config" -> {
        if (args.length < 4) {
          player.sendMessage("Usage: /world config <slug> <key> <value>");
          return true;
        }

        String worldSlug = args[1];
        String key = args[2].toLowerCase();

        Map<WorldConfigKey, Object> updates = new java.util.HashMap<>();

        switch (key) {
          case "name" -> {
            updates.put(WorldConfigKey.WORLD_NAME, args[3]);
          }

          case "maxplayers" -> {
            updates.put(WorldConfigKey.MAX_PLAYERS, Integer.parseInt(args[3]));
          }

          case "password" -> {
            if (args[3].equalsIgnoreCase("on")) {
              if (args.length < 5) {
                player.sendMessage("Usage: /world config <slug> password on <pw>");
                return true;
              }
              updates.put(WorldConfigKey.PASSWORD_ENABLED, true);
              updates.put(WorldConfigKey.PASSWORD, args[4]);
            } else if (args[3].equalsIgnoreCase("off")) {
              updates.put(WorldConfigKey.PASSWORD_ENABLED, false);
            }
          }

          default -> {
            player.sendMessage("Unknown config key.");
            return true;
          }
        }

        actionService.requestUpdateWorldConfig(uuid, worldSlug, updates);
      }

      default -> player.sendMessage("Unknown subcommand.");

    }
    return true;
  }
}
