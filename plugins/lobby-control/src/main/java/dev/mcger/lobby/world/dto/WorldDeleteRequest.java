package dev.mcger.lobby.world.dto;

import java.util.UUID;

public class WorldDeleteRequest {

  private final UUID ownerUuid;
  private final String worldSlug;

  public WorldDeleteRequest(UUID ownerUuid, String worldSlug) {
    this.ownerUuid = ownerUuid;
    this.worldSlug = worldSlug;
  }

  public UUID getOwnerUuid() {
    return ownerUuid;
  }

  public String getWorldSlug() {
    return worldSlug;
  }
}
