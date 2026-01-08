package dev.mcger.lobby.world.dto;

import java.util.UUID;
import dev.mcger.protocol.world.WorldStatus;

public class WorldUpdateStatusRequest {

  private final UUID ownerUuid;
  private final String worldSlug;
  private final WorldStatus newStatus;

  public WorldUpdateStatusRequest(UUID ownerUuid, String worldSlug, WorldStatus newStatus) {
    this.ownerUuid = ownerUuid;
    this.worldSlug = worldSlug;
    this.newStatus = newStatus;
  }

  public UUID getOwnerUuid() {
    return ownerUuid;
  }

  public String getWorldSlug() {
    return worldSlug;
  }

  public WorldStatus getNewStatus() {
    return newStatus;
  }
}
