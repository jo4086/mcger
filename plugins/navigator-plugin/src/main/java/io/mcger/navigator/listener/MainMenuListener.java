package io.mcger.navigator.listener;

import io.mcger.core.bridge.plugin.PluginBridge;
import io.mcger.core.runtime.RuntimeContext;
import io.mcger.navigator.ui.MainMenu;
import io.mcger.navigator.ui.MainMenuHolder;
import io.mcger.navigator.ui.PlaceholderMenu;
import io.mcger.navigator.ui.WorldListMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class MainMenuListener implements Listener {

  private final WorldListMenu worldListMenu;
  private final PlaceholderMenu placeholderMenu;

  public MainMenuListener(WorldListMenu worldListMenu, PlaceholderMenu placeholderMenu) {
    this.worldListMenu = worldListMenu;
    this.placeholderMenu = placeholderMenu;
  }

  @EventHandler
  public void onClick(InventoryClickEvent event) {
    Inventory top = event.getView().getTopInventory();
    if (!(top.getHolder() instanceof MainMenuHolder)) {
      return;
    }

    event.setCancelled(true);

    if (!(event.getWhoClicked() instanceof Player)) {
      return;
    }

    if (event.getRawSlot() >= top.getSize()) {
      return;
    }

    RuntimeContext runtime = PluginBridge.runtime();
    if (runtime == null) {
      return;
    }

    Player player = (Player) event.getWhoClicked();
    int slot = event.getRawSlot();

    if (slot == MainMenu.SERVERS_SLOT) {
      worldListMenu.open(player, runtime, 0);
      return;
    }

    if (slot == MainMenu.MY_WORLD_SLOT) {
      placeholderMenu.open(
          player,
          Component.text("My World", NamedTextColor.GREEN),
          Component.text("My world UI will be here.", NamedTextColor.GRAY));
      return;
    }

    if (slot == MainMenu.FRIENDS_SLOT) {
      placeholderMenu.open(player,
          Component.text("Friends", NamedTextColor.LIGHT_PURPLE),
          Component.text("Friends UI will be here.", NamedTextColor.GRAY));
      return;
    }

    if (slot == MainMenu.LOBBY_SLOT) {
      player.closeInventory();
      player.sendMessage(Component.text("Returned to lobby.", NamedTextColor.YELLOW));
    }
  }
}
