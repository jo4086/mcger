package dev.mcger.lobby.websocket;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class LobbyWebSocketClient extends WebSocketClient {

  public LobbyWebSocketClient(URI serverUri) {
    super(serverUri);
  }

  @Override
  public void onOpen(ServerHandshake handshake) {
    System.out.println("[WS] Connected to server");
  }

  @Override
  public void onMessage(String message) {
    // TODO: ResultHandler로 전달
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    System.out.println("[WS] Disconnected: " + reason);
  }

  @Override
  public void onError(Exception ex) {
    ex.printStackTrace();
  }
}
