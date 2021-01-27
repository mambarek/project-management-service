package com.it2go.micro.projectmanagement.services.messagin;

/**
 * created by mmbarek on 18.01.2021.
 */
public class ReceiveMessageException extends Exception{

  public ReceiveMessageException(String message) {
    super(message);
  }

  public ReceiveMessageException(String message, Throwable cause) {
    super(message, cause);
  }
}
