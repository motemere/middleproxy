package me.motemere.middleproxy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  @Value(value = "${websocket.url.path}")
  private String path;

  @Autowired
  private WebSocketHandler messageHandler;

  /**
   * Register the WebSocketHandler with the WebSocketHandlerRegistry.
   *
   * @param registry the WebSocketHandlerRegistry.
   */
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(messageHandler, path)
        .setAllowedOrigins("*");
  }

}
