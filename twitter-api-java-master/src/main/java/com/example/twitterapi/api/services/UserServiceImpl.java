package com.example.twitterapi.api.services;

import com.example.twitterapi.api.bean.*;
import com.example.twitterapi.api.bean.twitter.ErrorResponseUser;
import com.example.twitterapi.api.bean.twitter.User;
import com.example.twitterapi.api.command.Command;
import com.example.twitterapi.api.command.CommandUrl;
import com.example.twitterapi.api.repository.UserRepository;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.twitterapi.api.helper.ResponseUtils.API_RESPONSE_BAD_REQUEST;
import static com.example.twitterapi.api.helper.ResponseUtils.DEFAULT_ERROR_RESPONSE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service
public class UserServiceImpl implements UserService {

    private final Command command;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(Command command, UserRepository userRepository) {
        this.command = command;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> getLast10TweetsByUser(String user) throws IOException, JSONException {
        HttpGet usersTw = new HttpGet((String.format(CommandUrl.SEARCH_USERS, user)));
        String userTwResp = command.executeHttpMethod(usersTw);
        JSONObject twUserJson = new JSONObject(userTwResp);

        ResponseEntity<ApiResponse> checkUserErrors = checkUserErrors(twUserJson, user);
        if (checkUserErrors != null) {
            return checkUserErrors;
        }
        User userTw = new Gson().fromJson(twUserJson.getString("data"), User.class);

        HttpGet getLastTweets = new HttpGet(String.format(CommandUrl.USER_TWEETS, userTw.getId()) + "?max_results=10");
        String lastTweetsResponse = command.executeHttpMethod(getLastTweets);
        List<ApiData> apiTweetResponseList = new ArrayList<>();

        try {
            JSONArray tweetsResponseJson = new JSONObject(lastTweetsResponse).getJSONArray("data");
            for (int i = 0; i < tweetsResponseJson.length(); i++) {
                JSONObject tweetJson = tweetsResponseJson.getJSONObject(i);
                apiTweetResponseList.add(new ApiTweetResponse(tweetJson.getString("id"), tweetJson.getString("text")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return DEFAULT_ERROR_RESPONSE;
        }
        return new ResponseEntity<>(new ApiResponse(true, apiTweetResponseList, HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getLast10FollowersAsc(String user) throws IOException, JSONException {
        HttpGet usersTw = new HttpGet((String.format(CommandUrl.SEARCH_USERS, user)));
        String userTwResp = command.executeHttpMethod(usersTw);
        JSONObject twUserJson = new JSONObject(userTwResp);

        ResponseEntity<ApiResponse> checkUserErrors = checkUserErrors(twUserJson, user);
        if (checkUserErrors != null) {
            return checkUserErrors;
        }
        User userTw = new Gson().fromJson(twUserJson.getString("data"), User.class);

        HttpGet getLastFollowers = new HttpGet(String.format(CommandUrl.USER_FOLLOWERS, userTw.getId()) + "?max_results=10");
        String lastFollowersResponse = command.executeHttpMethod(getLastFollowers);
        List<ApiFollowResponse> apiFollowResponseList = new ArrayList<>();

        JSONArray tweetsResponseJson = new JSONObject(lastFollowersResponse).getJSONArray("data");
        for (int i = 0; i < tweetsResponseJson.length(); i++) {
            JSONObject tweetJson = tweetsResponseJson.getJSONObject(i);
            apiFollowResponseList.add(new ApiFollowResponse(tweetJson.getString("name"), tweetJson.getString("username")));
        }
        apiFollowResponseList.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));

        return new ResponseEntity<>(new ApiResponse(true, new ArrayList<>(apiFollowResponseList), HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getLastLikes(String user) throws IOException, JSONException {
        HttpGet usersTw = new HttpGet((String.format(CommandUrl.SEARCH_USERS, user)));
        String userTwResp = command.executeHttpMethod(usersTw);
        JSONObject twUserJson = new JSONObject(userTwResp);

        ResponseEntity<ApiResponse> checkUserErrors = checkUserErrors(twUserJson, user);
        if (checkUserErrors != null) {
            return checkUserErrors;
        }
        User userTw = new Gson().fromJson(twUserJson.getString("data"), User.class);

        HttpGet getLastLikes = new HttpGet(String.format(CommandUrl.USER_LIKED_TWEET, userTw.getId()) + "?max_results=10");
        String lastLikesResponse = command.executeHttpMethod(getLastLikes);
        List<ApiData> apiLikesResponseList = new ArrayList<>();

        JSONArray tweetsResponseJson = new JSONObject(lastLikesResponse).getJSONArray("data");
        for (int i = 0; i < tweetsResponseJson.length(); i++) {
            JSONObject tweetJson = tweetsResponseJson.getJSONObject(i);
            apiLikesResponseList.add(new ApiTweetResponse(tweetJson.getString("id"), tweetJson.getString("text")));
        }

        return new ResponseEntity<>(new ApiResponse(true, apiLikesResponseList, HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getLastMentions(String user) throws JSONException, IOException {
        HttpGet usersTw = new HttpGet((String.format(CommandUrl.SEARCH_USERS, user)));
        String userTwResp = command.executeHttpMethod(usersTw);
        JSONObject twUserJson = new JSONObject(userTwResp);

        ResponseEntity<ApiResponse> checkUserErrors = checkUserErrors(twUserJson, user);
        if (checkUserErrors != null) {
            return checkUserErrors;
        }
        User userTw = new Gson().fromJson(twUserJson.getString("data"), User.class);

        HttpGet getLastRetweeted = new HttpGet(String.format(CommandUrl.USER_MENTIONS, userTw.getId()) + "?max_results=10");
        String lastRetweetedResponse = command.executeHttpMethod(getLastRetweeted);
        List<ApiData> apiRetweetedResponseList = new ArrayList<>();

        JSONArray tweetsResponseJson = new JSONObject(lastRetweetedResponse).getJSONArray("data");
        for (int i = 0; i < tweetsResponseJson.length(); i++) {
            JSONObject tweetJson = tweetsResponseJson.getJSONObject(i);
            apiRetweetedResponseList.add(new ApiTweetResponse(tweetJson.getString("id"), tweetJson.getString("text")));
        }

        return new ResponseEntity<>(new ApiResponse(true, apiRetweetedResponseList, HttpStatus.SC_OK), OK);
    }


    @Override
    public ResponseEntity<ApiResponse> save(Map<String, String> userMap) throws IOException {

        String user = userMap.getOrDefault("user", "");
        if (user.isEmpty()) {
            return API_RESPONSE_BAD_REQUEST;
        }
        String url = String.format(CommandUrl.SEARCH_USERS, user) + "?user.fields=public_metrics";
        HttpGet usersTw = new HttpGet(url);
        String userTwResp = command.executeHttpMethod(usersTw);
        JSONObject twUserJson;
        try {
            twUserJson = new JSONObject(userTwResp);
            ResponseEntity<ApiResponse> checkUserErrors = checkUserErrors(twUserJson, user);
            if (checkUserErrors != null) {
                return checkUserErrors;
            }
            User userTw = new Gson().fromJson(twUserJson.getString("data"), User.class);
            userRepository.findOneByUsername(userTw.getUsername()).ifPresent(userObj -> userTw.setId(userObj.getId()));
            userRepository.save(userTw);
            return new ResponseEntity<>(new ApiResponse(true, Collections.singletonList(new ApiSingleResponse("User Guardado.")),
                    HttpStatus.SC_OK), OK);
        } catch (JSONException e) {
            e.printStackTrace();
            return DEFAULT_ERROR_RESPONSE;
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getUsersOrderFollowers() {
        List<ApiData> apiData = new ArrayList<>(userRepository.findAllByOrderByPublicMetricsDesc());
        return new ResponseEntity<>(new ApiResponse(true, apiData, HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getUsersOrderName() {
        List<ApiData> apiData = new ArrayList<>(userRepository.findAllByOrderByName());
        return new ResponseEntity<>(new ApiResponse(true, apiData, HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> checkUserErrors(JSONObject twUserJson, String user) {
        try {

            if (twUserJson.has("errors")) {
                JSONArray errorsJsonArray = twUserJson.getJSONArray("errors");
                ErrorResponseUser errorResponseUser = new Gson().fromJson(errorsJsonArray.getString(0), ErrorResponseUser.class);
                if (errorResponseUser.getTitle().equalsIgnoreCase("Not Found Error")) {
                    ApiResponse apiResponse = new ApiResponse(false,
                            Collections.singletonList(new ApiSingleResponse(String.format("Usuario %s no encontrado.", user))),
                            HttpStatus.SC_NOT_FOUND);
                    return new ResponseEntity<>(apiResponse, NOT_FOUND);
                } else {
                    return DEFAULT_ERROR_RESPONSE;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return DEFAULT_ERROR_RESPONSE;
        }

        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> delete(Map<String, String> userMap) {
        String username = userMap.getOrDefault("user", "");
        if (username.isEmpty()) {
            return API_RESPONSE_BAD_REQUEST;
        }
        User user = userRepository.findOneByUsername(username).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse(false, Collections.singletonList(new ApiSingleResponse("User No encontrado.")),
                    HttpStatus.SC_NOT_FOUND), NOT_FOUND);
        }
        userRepository.delete(user);
        return new ResponseEntity<>(new ApiResponse(true, Collections.singletonList(new ApiSingleResponse("User Eliminado.")),
                HttpStatus.SC_OK), OK);
    }
}

