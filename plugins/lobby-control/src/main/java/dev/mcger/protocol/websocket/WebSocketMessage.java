package dev.mcger.protocol.websocket;

public class WebSocketMessage {
  private final String type;
  private final String requestId;
  private final Object payload;

  public WebSocketMessage(String type, String requestId, Object payload) {
    this.type = type;
    this.requestId = requestId;
    this.payload = payload;
  }

  public String getType() {
    return type;
  }

  public String getRequestId() {
    return requestId;
  }

  public Object getPayload() {
    return payload;
  }
}
