package com.svw.dealerapp.entity;

/**
 * Created by lijinkui on 2018/1/25.
 */

public class SMResEntity<T> {
    private String status;
    private String error;
    private String message;
    private String timestamp;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SMResEntity{" +
                "status='" + status + '\'' +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", data=" + data +
                '}';
    }
}
