package com.example.twitterapi.api.bean.twitter;

import com.example.twitterapi.api.bean.ApiData;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tweet")
public class Tweet extends ApiData {

    @Id
    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
