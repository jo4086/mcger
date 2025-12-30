package dev.mcger.lobby.world.dto;

import java.util.UUID;
import java.util.Map;

import dev.mcger.protocol.world.WorldConfigKey;

public class WorldUpdateConfigRequest {

  private final UUID ownerUuid;
  private final String worldSlug;
  private final Map<WorldConfigKey, Object> updates;

  public WorldUpdateConfigRequest(UUID ownerUuid, String worldSlug, Map<WorldConfigKey, Object> updates) {
    this.ownerUuid = ownerUuid;
    this.worldSlug = worldSlug;
    this.updates = updates;
  }

  public UUID getOwnerUuid() {
    return ownerUuid;
  }

  public String getWorldSlug() {
    return worldSlug;
  }

  public Map<WorldConfigKey, Object> getUpdates() {
    return updates;
  }
}
