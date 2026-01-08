package dev.mcger.lobby.world.dto;

import java.util.UUID;

public class WorldSearchRequest {

  private final UUID requesterUuid;
  private final String keyword;
  private final int page;
  private final int size;

  public WorldSearchRequest(
      UUID requesterUuid,
      String keyword,
      int page,
      int size) {
    this.requesterUuid = requesterUuid;
    this.keyword = keyword;
    this.page = page;
    this.size = size;
  }

  public UUID getRequesterUuid() {
    return requesterUuid;
  }

  public String getKeyword() {
    return keyword;
  }

  public int getPage() {
    return page;
  }

  public int getSize() {
    return size;
  }
}
