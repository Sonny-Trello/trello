package io.superson.trelloproject.global.exception;

public class UserPermissionException extends RuntimeException {
    public UserPermissionException(String message) {
        super(message);
    }
}
