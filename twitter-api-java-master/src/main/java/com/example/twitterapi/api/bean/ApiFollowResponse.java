package com.example.twitterapi.api.bean;

public class ApiFollowResponse extends ApiData {
    private String name;
    private String userName;

    public ApiFollowResponse(String name, String userName) {
        super();
        this.name = name;
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
