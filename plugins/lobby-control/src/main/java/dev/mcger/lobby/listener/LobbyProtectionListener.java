package dev.mcger.lobby.listener;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import dev.mcger.lobby.editor.LobbyEditorManager;

import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.entity.Player;

public class LobbyProtectionListener implements Listener {

  // 고정값: 로비 월드 이름
  private static final String LOBBY_WORLD = "world";

  private boolean isLobby(World world) {
    return world.getName().equalsIgnoreCase(LOBBY_WORLD);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!isLobby(event.getBlock().getWorld()))
      return;

    var player = event.getPlayer();

    if (LobbyEditorManager.isEditor(player.getUniqueId())) {
      return;
    }

    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {

    if (!isLobby(event.getBlock().getWorld()))
      return;

    var player = event.getPlayer();

    if (LobbyEditorManager.isEditor(player.getUniqueId())) {
      return;
    }

    event.setCancelled(true);
  }

  @EventHandler
  public void onDamage(EntityDamageEvent event) {
    if (isLobby(event.getEntity().getWorld()) && event.getEntity() instanceof org.bukkit.entity.Player) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onItemDrop(PlayerDropItemEvent event) {
    if (isLobby(event.getPlayer().getWorld())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onItemPickip(EntityPickupItemEvent event) {
    if (!(event.getEntity() instanceof Player player)) {
      return;
    }

    if (isLobby(player.getWorld())) {
      event.setCancelled(true);
    }
  }
}
