package com.example.twitterapi.api.controller;

import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.services.UserService;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserAPI {

    private final UserService userService;

    @Autowired
    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/tweets/{user}")
    public ResponseEntity<ApiResponse> getLast10TweetsByUser(@PathVariable String user) throws IOException, JSONException {
        return userService.getLast10TweetsByUser(user);
    }

    @GetMapping("/followers_asc/{user}")
    public ResponseEntity<ApiResponse> getLast10FollowersAsc(@PathVariable String user) throws IOException, JSONException {
        return userService.getLast10FollowersAsc(user);
    }

    @GetMapping("/likes/{user}")
    public ResponseEntity<ApiResponse> lastLikes(@PathVariable String user) throws IOException, JSONException {
        return userService.getLastLikes(user);
    }

    @GetMapping("/mentions/{user}")
    public ResponseEntity<ApiResponse> lastMentions(@PathVariable String user) throws IOException, JSONException {
        return userService.getLastMentions(user);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody Map<String, String> userMap) throws IOException {
        return userService.save(userMap);
    }

    @GetMapping("/get_users_order_followers")
    public ResponseEntity<ApiResponse> getUsersOrderFollowers() {
        return userService.getUsersOrderFollowers();
    }

    @GetMapping("/get_users_order_name")
    public ResponseEntity<ApiResponse> getUsersOrderName() {
        return userService.getUsersOrderName();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUsername(@RequestBody Map<String, String> userMap) {
        return userService.delete(userMap);
    }
}

