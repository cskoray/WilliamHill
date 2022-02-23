package com.williamHill.scoreBoard.config;

import static java.util.UUID.randomUUID;

import com.sun.security.auth.UserPrincipal;
import java.security.Principal;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

  @Override
  protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
      Map<String, Object> attributes) {

    final String randomId = randomUUID().toString();
    return new UserPrincipal(randomId);
  }
}