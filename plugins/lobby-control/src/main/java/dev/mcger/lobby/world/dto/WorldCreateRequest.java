package dev.mcger.lobby.world.dto;

import java.util.UUID;

import dev.mcger.protocol.world.WorldType;

public class WorldCreateRequest {

  private final UUID ownerUuid;
  private final String worldName;
  private final WorldType worldType;

  public WorldCreateRequest(UUID ownerUuid, String worldName, WorldType worldType) {
    this.ownerUuid = ownerUuid;
    this.worldName = worldName;
    this.worldType = worldType;
  }

  public UUID getOwnerUuid() {
    return ownerUuid;
  }

  public String getWorldName() {
    return worldName;
  }

  public WorldType getWorldType() {
    return worldType;
  }
}
