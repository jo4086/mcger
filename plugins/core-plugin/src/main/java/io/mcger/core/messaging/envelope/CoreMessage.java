package io.mcger.core.messaging.envelope;

import io.mcger.core.messaging.MessageType;
import java.util.UUID;

/**
 * Core 메시지 공통 Envelope
 *
 * 모든 내부/외부 메시지는 이 형태를 따른다.
 */
public class CoreMessage {

  private final MessageType type;
  private final String requestId;
  private final Object payload;

  public CoreMessage(MessageType type, Object payload) {
    this.type = type;
    this.payload = payload;
    this.requestId = UUID.randomUUID().toString();
  }

  public MessageType getType() {
    return type;
  }

  public String getRequestId() {
    return requestId;
  }

  public Object getPayload() {
    return payload;
  }
}
