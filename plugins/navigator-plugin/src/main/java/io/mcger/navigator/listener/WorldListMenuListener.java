package io.mcger.navigator.listener;

import io.mcger.core.bridge.plugin.PluginBridge;
import io.mcger.core.runtime.RuntimeContext;
import io.mcger.navigator.ui.WorldListHolder;
import io.mcger.navigator.ui.WorldListMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class WorldListMenuListener implements Listener {

  private final WorldListMenu menu;

  public WorldListMenuListener(WorldListMenu menu) {
    this.menu = menu;
  }

  @EventHandler
  public void onClick(InventoryClickEvent event) {
    Inventory top = event.getView().getTopInventory();
    if (!(top.getHolder() instanceof WorldListHolder)) {
      return;
    }

    event.setCancelled(true);

    if (!(event.getWhoClicked() instanceof Player)) {
      return;
    }

    if (event.getRawSlot() >= top.getSize()) {
      return;
    }

    WorldListHolder holder = (WorldListHolder) top.getHolder();
    int currentPage = holder.getPage();
    int clickedSlot = event.getRawSlot();

    RuntimeContext runtime = PluginBridge.runtime();
    if (runtime == null) {
      return;
    }

    int pageCount = Math.max(1,
        (int) Math.ceil(runtime.getWorldSummaries().size() / (double) WorldListMenu.PAGE_SIZE));

    if (clickedSlot == 45 && currentPage > 0) {
      menu.open((Player) event.getWhoClicked(), runtime, currentPage - 1);
    }

    if (clickedSlot == 53 && currentPage < pageCount - 1) {
      menu.open((Player) event.getWhoClicked(), runtime, currentPage + 1);
    }
  }
}
