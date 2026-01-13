package io.mcger.navigator.ui;

import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceholderMenu {

  private static final int INVENTORY_SIZE = 27;

  public void open(Player player, Component title, Component message) {
    PlaceholderHolder holder = new PlaceholderHolder();
    Inventory inventory = Bukkit.createInventory(holder, INVENTORY_SIZE, title);
    holder.setInventory(inventory);

    ItemStack item = new ItemStack(Material.PAPER);
    ItemMeta meta = item.getItemMeta();
    meta.displayName(Component.text("Coming Soon", NamedTextColor.GRAY));
    meta.lore(List.of(message));
    item.setItemMeta(meta);

    inventory.setItem(13, item);
    player.openInventory(inventory);
  }

  private static class PlaceholderHolder implements InventoryHolder {
    private Inventory inventory;

    private void setInventory(Inventory inventory) {
      this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
      return inventory;
    }
  }
}
