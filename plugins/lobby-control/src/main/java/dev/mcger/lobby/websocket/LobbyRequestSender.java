package dev.mcger.lobby.websocket;

import java.util.UUID;
import com.google.gson.Gson;

import dev.mcger.protocol.websocket.WebSocketMessage;

public class LobbyRequestSender {
  private final LobbyWebSocketClient client;
  private final Gson gson = new Gson();

  public LobbyRequestSender(LobbyWebSocketClient client) {
    this.client = client;
  }

  public void send(Enum<?> type, Object payload) {
    WebSocketMessage message = new WebSocketMessage(
        type.name(),
        UUID.randomUUID().toString(),
        payload);

    String json = gson.toJson(message);
    client.send(json);
  }
}

// public class LobbyRequestSender {
//
// public void send(Enum<?> type, Object payload) {
// // TODO: websocket send
// }
// }
