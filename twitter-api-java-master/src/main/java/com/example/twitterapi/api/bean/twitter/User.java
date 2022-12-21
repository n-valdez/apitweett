package com.example.twitterapi.api.bean.twitter;

import com.example.twitterapi.api.bean.ApiData;
import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User extends ApiData {

    @Id
    private String id;
    private String name;
    private String username;

    @SerializedName("public_metrics")
    private PublicMetrics publicMetrics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PublicMetrics getPublicMetrics() {
        return publicMetrics;
    }

    public void setPublicMetrics(PublicMetrics publicMetrics) {
        this.publicMetrics = publicMetrics;
    }
}
