package com.bitfiddling.helidon.service;

import io.helidon.http.Status;

public class JsonHttpException extends RuntimeException {
    private final Object body;
    private final boolean keepAlive;
    private final Status status;

    public JsonHttpException(Object message) {
        this(message, Status.INTERNAL_SERVER_ERROR_500, null);
    }

    public JsonHttpException(Object message, Throwable cause) {
        this(message, Status.INTERNAL_SERVER_ERROR_500, cause);
    }

    public JsonHttpException(Object message, Status status) {
        this(message, status, null);
    }

    public JsonHttpException(Object message, Status status, boolean keepAlive) {
        this(message, status, null, keepAlive);
    }

    public JsonHttpException(Object message, Status status, Throwable cause) {
        this(message, status, cause, false);
    }

    public JsonHttpException(Object message, Status status, Throwable cause, boolean keepAlive) {
        super(cause);

        this.body = message;
        this.status = status;
        this.keepAlive = keepAlive;
    }

    public Object body() {
        return body;
    }

    public boolean keepAlive() {
        return keepAlive;
    }

    public Status status() {
        return status;
    }
}
