package io.mcger.navigator.ui;

import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu {

  public static final int INVENTORY_SIZE = 27;
  public static final int SERVERS_SLOT = 10;
  public static final int MY_WORLD_SLOT = 12;
  public static final int FRIENDS_SLOT = 14;
  public static final int LOBBY_SLOT = 16;

  public void open(Player player) {
    Inventory inventory = buildInventory();
    player.openInventory(inventory);
  }

  public Inventory buildInventory() {
    MainMenuHolder holder = new MainMenuHolder();
    Component title = Component.text(
        "Quick Travel",
        NamedTextColor.DARK_AQUA);
    Inventory inventory = Bukkit.createInventory(
        holder,
        INVENTORY_SIZE,
        title);
    holder.setInventory(inventory);

    inventory.setItem(
        SERVERS_SLOT,
        createItem(
            Material.ENDER_EYE,
            Component.text("Servers", NamedTextColor.AQUA),
            List.of(Component.text("Browse all worlds", NamedTextColor.GRAY))));

    inventory.setItem(
        MY_WORLD_SLOT,
        createItem(
            Material.GRASS_BLOCK,
            Component.text("My World", NamedTextColor.GREEN),
            List.of(Component.text("Your owned worlds", NamedTextColor.GRAY))));

    inventory.setItem(
        FRIENDS_SLOT,
        createItem(
            Material.PLAYER_HEAD,
            Component.text("Friends", NamedTextColor.LIGHT_PURPLE),
            List.of(Component.text("Friends list", NamedTextColor.GRAY))));

    inventory.setItem(
        LOBBY_SLOT,
        createItem(
            Material.NETHER_STAR,
            Component.text("Lobby", NamedTextColor.YELLOW),
            List.of(Component.text("Return to lobby", NamedTextColor.GRAY))));

    return inventory;
  }

  private ItemStack createItem(Material material, Component name, List<Component> lore) {
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
