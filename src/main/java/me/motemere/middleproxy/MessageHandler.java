package me.motemere.middleproxy;

import me.motemere.testproject.dto.Message;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MessageHandler extends TextWebSocketHandler {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Value(value = "${message.topic.name}")
  private String topicName;

  private static final Logger LOG = Logger.getLogger(MessageHandler.class.getName());

  private final List<WebSocketSession> webSocketSessions = Collections.synchronizedList(
      new ArrayList<>());

  /**
   * Method to add a new WebSocketSession to the list of connected clients.
   *
   * @param session the WebSocketSession to add.
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    super.afterConnectionEstablished(session);
    webSocketSessions.add(session);

    LOG.info("Socket connected: " + session);
  }

  /**
   * Method to remove a WebSocketSession from the list of connected clients.
   *
   * @param session the WebSocketSession to remove.
   * @param status  the status of the connection.
   */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    LOG.info(String.format("Socket closed: [%s], [%s]", status.getReason(), status.getCode()));

    super.afterConnectionClosed(session, status);
    webSocketSessions.remove(session);
  }

  /**
   * Method to handle the incoming message from the client.
   *
   * @param session the WebSocketSession of the client.
   * @param message the message from the client.
   */
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    super.handleTextMessage(session, message);

    for (WebSocketSession webSocketSession : webSocketSessions) {
      webSocketSession.sendMessage(message);
    }

    LOG.info("Received: " + message.getPayload());

    ObjectMapper objectMapper = new ObjectMapper();
    Message messageObj = objectMapper.readValue(message.getPayload(), Message.class);
    messageObj.setMiddleProxyTimestamp(System.currentTimeMillis());

    LOG.info("MessageObj: " + messageObj);

    // Send the message to Kafka
    kafkaTemplate.send(topicName, messageObj.toJson());
  }
}
