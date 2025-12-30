package dev.mcger.lobby.world.service;

import java.util.Map;
import java.util.UUID;

import dev.mcger.protocol.MessageType;
import dev.mcger.protocol.world.WorldStatus;
import dev.mcger.protocol.world.WorldType;
import dev.mcger.protocol.world.WorldConfigKey;

import dev.mcger.lobby.websocket.LobbyRequestSender;
import dev.mcger.lobby.world.dto.WorldCreateRequest;
import dev.mcger.lobby.world.dto.WorldDeleteRequest;
import dev.mcger.lobby.world.dto.WorldJoinRequest;
import dev.mcger.lobby.world.dto.WorldUpdateStatusRequest;
import dev.mcger.lobby.world.dto.WorldUpdateConfigRequest;
import dev.mcger.lobby.world.validator.WorldConfigUpdateValidator;

public class LobbyWorldActionService {

  private final LobbyRequestSender sender;

  public LobbyWorldActionService(LobbyRequestSender sender) {
    this.sender = sender;
  }

  public void requestCreateWorld(
      UUID ownerUuid,
      String worldName,
      WorldType worldType) {

    sender.send(
        MessageType.WORLD_CREATE_REQUEST,
        new WorldCreateRequest(ownerUuid, worldName, worldType));
  }

  public void requestJoinWorld(
      UUID userUuid,
      String worldSlug) {

    sender.send(
        MessageType.WORLD_JOIN_REQUEST,
        new WorldJoinRequest(userUuid, worldSlug));
  }

  public void requestDeleteWorld(
      UUID ownerUuid,
      String worldSlug) {

    sender.send(
        MessageType.WORLD_DELETE_REQUEST,
        new WorldDeleteRequest(ownerUuid, worldSlug));
  }

  public void requestUpdateWorldStatus(
      UUID ownerUuid,
      String worldSlug,
      WorldStatus newStatus) {

    sender.send(MessageType.WORLD_UPDATE_STATUS_REQUEST, new WorldUpdateStatusRequest(ownerUuid, worldSlug, newStatus));
  }

  public void requestUpdateWorldConfig(
      UUID ownerUuid,
      String worldSlug,
      Map<WorldConfigKey, Object> updates) {
    WorldConfigUpdateValidator.validate(updates);

    sender.send(
        MessageType.WORLD_UPDATE_CONFIG_REQUEST,
        new WorldUpdateConfigRequest(ownerUuid, worldSlug, updates));
  }
}
