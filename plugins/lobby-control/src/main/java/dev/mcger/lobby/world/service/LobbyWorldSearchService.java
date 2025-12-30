package dev.mcger.lobby.world.service;

import dev.mcger.protocol.MessageType;

import java.util.UUID;

import dev.mcger.lobby.websocket.LobbyRequestSender;
import dev.mcger.lobby.world.dto.WorldSearchRequest;

public class LobbyWorldSearchService {

  private final LobbyRequestSender sender;

  public LobbyWorldSearchService(LobbyRequestSender sender) {
    this.sender = sender;
  }

  /**
   * 월드 검색 요청
   *
   * @param requesterUuid 검색을 요청한 유저 UUID (고정값)
   * @param keyword       검색 키워드 (예시값)
   * @param page          페이지 번호 (예시값)
   * @param size          페이지당 결과 수 (예시값)
   */

  public void requestSearchWorlds(
      UUID requesterUuid,
      String keyword,
      int page,
      int size) {
    sender.send(
        MessageType.WORLD_SEARCH_REQUEST,
        new WorldSearchRequest(
            requesterUuid,
            keyword,
            page,
            size));
  }
}
