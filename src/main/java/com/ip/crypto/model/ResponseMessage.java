package com.ip.crypto.model;

/**
 * Message to client that we’re going to use in Rest Controller and Exception Handler.
 */
public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
