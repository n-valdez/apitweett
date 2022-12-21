package com.example.twitterapi.api.controller;

import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.services.TweetService;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/tweet")
public class TweetAPI {

    private final TweetService tweetService;

    @Autowired
    public TweetAPI(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveById(@RequestBody Map<String, String> tweetMap) throws IOException {
        return tweetService.save(tweetMap);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteById(@RequestBody Map<String, String> tweetMap) {
        return tweetService.delete(tweetMap);
    }

    @GetMapping("/get_all")
    public ResponseEntity<ApiResponse> getAll() {
        return tweetService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTweetById(@PathVariable String id) {
        return tweetService.getTweetById(id);
    }
}
