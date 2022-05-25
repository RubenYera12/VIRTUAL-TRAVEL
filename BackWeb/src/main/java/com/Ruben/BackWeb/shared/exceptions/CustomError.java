package com.Ruben.BackWeb.shared.exceptions;

import java.util.Date;

public class CustomError {
    private Date timestamp;
    int HttpStatus;
    private String message;

    public CustomError(Date timestamp, int HttpStatus, String message) {
        super();
        this.timestamp = timestamp;
        this.HttpStatus = HttpStatus;
        this.message = message;

    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getHttpStatus() {
        return HttpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        HttpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}