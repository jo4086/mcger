package io.mcger.core.runtime;

import java.util.UUID;

public class WorldSummary {

  private final String worldId;
  private final String name;
  private final UUID ownerUuid;
  private final int maxPlayers;
  private final boolean passwordEnabled;
  private final boolean whitelistEnabled;

  public WorldSummary(
      String worldId,
      String name,
      UUID ownerUuid,
      int maxPlayers,
      boolean passwordEnabled,
      boolean whitelistEnabled) {
    this.worldId = worldId;
    this.name = name;
    this.ownerUuid = ownerUuid;
    this.maxPlayers = maxPlayers;
    this.passwordEnabled = passwordEnabled;
    this.whitelistEnabled = whitelistEnabled;
  }

  public String getWorldId() {
    return worldId;
  }

  public String getName() {
    return name;
  }

  public UUID getOwnerUuid() {
    return ownerUuid;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public boolean isPasswordEnabled() {
    return passwordEnabled;
  }

  public boolean isWhitelistEnabled() {
    return whitelistEnabled;
  }
}
