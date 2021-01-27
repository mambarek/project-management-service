package com.it2go.micro.projectmanagement.services.messagin;

/**
 * created by mmbarek on 24.01.2021.
 */
public interface MessageService {

  void sendMessage(String queueName, Object body) throws SendMessageException;
  Object receiveAndConvertMessage(String queueName) throws ReceiveMessageException;
}
