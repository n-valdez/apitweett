package com.example.twitterapi.api.bean;

import org.springframework.data.mongodb.core.mapping.Document;


@Document("twitterapi")
public class TestDB {
    private String name;

    public TestDB(String name) {
        this.name = name;
    }
}
