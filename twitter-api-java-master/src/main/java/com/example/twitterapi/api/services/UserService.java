package com.example.twitterapi.api.services;

import com.example.twitterapi.api.bean.ApiResponse;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public interface UserService {
    ResponseEntity<ApiResponse> getLast10TweetsByUser(String user) throws IOException, JSONException;

    ResponseEntity<ApiResponse> getLast10FollowersAsc(String user) throws IOException, JSONException;

    ResponseEntity<ApiResponse> getLastLikes(String user) throws IOException, JSONException;

    ResponseEntity<ApiResponse> getLastMentions(String user) throws JSONException, IOException;

    ResponseEntity<ApiResponse> save(Map<String, String> userMap) throws IOException;

    ResponseEntity<ApiResponse> getUsersOrderFollowers();

    ResponseEntity<ApiResponse> getUsersOrderName();

    ResponseEntity<ApiResponse> checkUserErrors(JSONObject twUserJson, String user);

    ResponseEntity<ApiResponse> delete(Map<String, String> userMap);
}
