package io.mcger.navigator.ui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MainMenuHolder implements InventoryHolder {

  private Inventory inventory;

  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }
}
