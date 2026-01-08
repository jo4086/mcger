package dev.mcger.lobby.world.dto;

import java.util.UUID;

public class WorldJoinRequest {

  private final UUID userUuid;
  private final String worldSlug;

  public WorldJoinRequest(UUID userUuid, String worldSlug) {
    this.userUuid = userUuid;
    this.worldSlug = worldSlug;
  }

  public UUID getUserUuid() {
    return userUuid;
  }

  public String getWorldSlug() {
    return worldSlug;
  }
}
