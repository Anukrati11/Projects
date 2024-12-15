package com.example.AppInstallationSystem.exception;

public class MessagingException extends Exception {
    public MessagingException(String message) {
        super(message);
    }

    public MessagingException(String message, Throwable cause) {
        super(message, cause);
    }
}
