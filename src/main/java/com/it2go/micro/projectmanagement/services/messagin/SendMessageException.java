package com.it2go.micro.projectmanagement.services.messagin;

/**
 * created by mmbarek on 18.01.2021.
 */
public class SendMessageException extends Exception{

  public SendMessageException(String message) {
    super(message);
  }

  public SendMessageException(String message, Throwable cause) {
    super(message, cause);
  }
}
