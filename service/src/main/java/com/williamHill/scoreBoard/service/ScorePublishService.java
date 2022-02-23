package com.williamHill.scoreBoard.service;

import com.williamHill.scoreBoard.dto.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScorePublishService {

  private final SimpMessagingTemplate messagingTemplate;

  @Autowired
  public ScorePublishService(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  public void notifyFrontend(final String message) {
    ResponseMessage response = new ResponseMessage(message);
    messagingTemplate.convertAndSend("/topic/scores", response);
  }
}