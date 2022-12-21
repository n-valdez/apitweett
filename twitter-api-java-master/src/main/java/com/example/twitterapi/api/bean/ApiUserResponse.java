package com.example.twitterapi.api.bean;

public class ApiUserResponse extends ApiData {
    private String message;

    public ApiUserResponse(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
