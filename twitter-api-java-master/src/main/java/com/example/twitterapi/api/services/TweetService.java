package com.example.twitterapi.api.services;

import com.example.twitterapi.api.bean.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public interface TweetService {
    ResponseEntity<ApiResponse> save(Map<String, String> tweetMap) throws IOException;

    ResponseEntity<ApiResponse> delete(Map<String, String> tweetMap);

    ResponseEntity<ApiResponse> getAll();

    ResponseEntity<ApiResponse> getTweetById(String id);
}
