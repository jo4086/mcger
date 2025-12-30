package dev.mcger.lobby.world.service;

import java.util.UUID;

import dev.mcger.protocol.MessageType;
import dev.mcger.lobby.world.dto.WorldListRequest;
import dev.mcger.lobby.websocket.LobbyRequestSender;

public class LobbyWorldQueryService {

  private final LobbyRequestSender sender;

  public LobbyWorldQueryService(LobbyRequestSender sender) {
    this.sender = sender;
  }

  public void requestMyWorldList(UUID userUuid) {
    sender.send(
        MessageType.WORLD_LIST_REQUEST,
        new WorldListRequest(userUuid));
  }
}
