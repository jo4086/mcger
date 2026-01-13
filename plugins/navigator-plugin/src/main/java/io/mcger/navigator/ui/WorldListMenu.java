package io.mcger.navigator.ui;

import io.mcger.core.runtime.RuntimeContext;
import io.mcger.core.runtime.WorldSummary;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/*
 * functions
 * - open
 * - buildInventory
 * - createWorldItem
 * - shortUuid
 * - placeNavItems
 * - flag
 * - createInfoItem
 * */

public class WorldListMenu {

  public static final int INVENTORY_SIZE = 54;
  public static final int PAGE_SIZE = 45;

  public void open(Player player, RuntimeContext runtime, int page) {
    Inventory inventory = buildInventory(runtime, page);
    player.openInventory(inventory);
  }

  public Inventory buildInventory(RuntimeContext runtime, int page) {
    int safePage = Math.max(0, page);
    WorldListHolder holder = new WorldListHolder(safePage);
    Component title = Component.text("World List ", NamedTextColor.DARK_AQUA)
        .append(Component.text("(Page " + (safePage + 1) + ")", NamedTextColor.GRAY));
    Inventory inventory = Bukkit.createInventory(holder, INVENTORY_SIZE, title);
    holder.setInventory(inventory);

    List<WorldSummary> worlds = runtime.getWorldSummaries();
    if (worlds.isEmpty()) {
      inventory.setItem(22, createInfoItem(
          Material.BARRIER,
          Component.text("No worlds found", NamedTextColor.RED),
          List.of(Component.text("Create a world to get started.", NamedTextColor.GRAY))));
      placeNavItems(inventory, safePage, 1);
      return inventory;
    }

    int totalPages = (int) Math.ceil(worlds.size() / (double) PAGE_SIZE);
    int startIndex = safePage * PAGE_SIZE;
    int endIndex = Math.min(startIndex + PAGE_SIZE, worlds.size());

    if (startIndex >= worlds.size()) {
      startIndex = Math.max(0, (totalPages - 1) * PAGE_SIZE);
      endIndex = Math.min(startIndex + PAGE_SIZE, worlds.size());
      safePage = Math.max(0, totalPages - 1);
    }

    int slot = 0;
    for (int i = startIndex; i < endIndex; i++) {
      WorldSummary summary = worlds.get(i);
      inventory.setItem(slot++, createWorldItem(summary));
    }

    placeNavItems(inventory, safePage, totalPages);
    return inventory;
  }

  private ItemStack createWorldItem(WorldSummary summary) {
    ItemStack item = new ItemStack(Material.GRASS_BLOCK);
    ItemMeta meta = item.getItemMeta();
    meta.displayName(Component.text(summary.getName(), NamedTextColor.GREEN));

    List<Component> lore = new ArrayList<>();
    lore.add(Component.text("Owner: ", NamedTextColor.GRAY)
        .append(Component.text(shortUuid(summary.getOwnerUuid()), NamedTextColor.WHITE)));
    lore.add(Component.text("Max Players: ", NamedTextColor.GRAY)
        .append(Component.text(summary.getMaxPlayers(), NamedTextColor.WHITE)));
    lore.add(Component.text("Password: ", NamedTextColor.GRAY)
        .append(flag(summary.isPasswordEnabled())));
    lore.add(Component.text("Whitelist: ", NamedTextColor.GRAY)
        .append(flag(summary.isWhitelistEnabled())));
    meta.lore(lore);
    item.setItemMeta(meta);
    return item;
  }

  private String shortUuid(java.util.UUID uuid) {
    if (uuid == null) {
      return "unknown";
    }
    String value = uuid.toString();
    return value.substring(0, 8);
  }

  private Component flag(boolean enabled) {
    return enabled
        ? Component.text("ON", NamedTextColor.GREEN)
        : Component.text("OFF", NamedTextColor.RED);
  }

  private void placeNavItems(Inventory inventory, int page, int totalPages) {
    if (page > 0) {
      inventory.setItem(45, createInfoItem(
          Material.ARROW,
          Component.text("Previous Page", NamedTextColor.YELLOW),
          List.of()));
    } else {
      inventory.setItem(45, createInfoItem(
          Material.GRAY_DYE,
          Component.text("Previous Page", NamedTextColor.DARK_GRAY),
          List.of()));
    }

    inventory.setItem(49, createInfoItem(
        Material.PAPER,
        Component.text("Page " + (page + 1) + "/" + totalPages, NamedTextColor.AQUA),
        List.of(Component.text("Use arrows to navigate", NamedTextColor.GRAY))));

    if (page < totalPages - 1) {
      inventory.setItem(53, createInfoItem(
          Material.ARROW,
          Component.text("Next Page", NamedTextColor.YELLOW),
          List.of()));
    } else {
      inventory.setItem(53, createInfoItem(
          Material.GRAY_DYE,
          Component.text("Next Page", NamedTextColor.DARK_GRAY),
          List.of()));
    }
  }

  private ItemStack createInfoItem(Material material, Component name, List<Component> lore) {
    ItemStack item = new ItemStack(material);
    ItemMeta meta = item.getItemMeta();
    meta.displayName(name);
    if (!lore.isEmpty()) {
      meta.lore(lore);
    }
    item.setItemMeta(meta);
    return item;
  }
}
