package dev.mcger.lobby.world.dto;

import java.util.UUID;

public class WorldListRequest {

  private final UUID userUuid;

  public WorldListRequest(UUID userUuid) {
    this.userUuid = userUuid;
  }

  public UUID getUserUuid() {
    return userUuid;
  }
}
