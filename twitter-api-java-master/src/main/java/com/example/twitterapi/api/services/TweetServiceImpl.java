package com.example.twitterapi.api.services;

import com.example.twitterapi.api.bean.ApiData;
import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.bean.ApiSingleResponse;
import com.example.twitterapi.api.bean.twitter.Tweet;
import com.example.twitterapi.api.command.Command;
import com.example.twitterapi.api.command.CommandUrl;
import com.example.twitterapi.api.repository.TweetRepository;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
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
public class TweetServiceImpl implements TweetService {

    private final Command command;
    private final TweetRepository tweetRepository;

    @Autowired
    public TweetServiceImpl(Command command, TweetRepository tweetRepository) {
        this.command = command;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> save(Map<String, String> tweetMap) throws IOException {

        String id = tweetMap.getOrDefault("id", "");
        if (id.isEmpty()) {
            return API_RESPONSE_BAD_REQUEST;
        }

        String url = String.format(CommandUrl.GET_TWEET_BY_ID, id);
        HttpGet tweetTwGet = new HttpGet(url);
        String tweetTwResp = command.executeHttpMethod(tweetTwGet);
        JSONObject twTweetJson;
        try {
            twTweetJson = new JSONObject(tweetTwResp);
            Tweet tweetTw = new Gson().fromJson(twTweetJson.getString("data"), Tweet.class);
            tweetRepository.findOneById(tweetTw.getId()).ifPresent(twObj -> tweetTw.setId(twObj.getId()));
            tweetRepository.save(tweetTw);
            return new ResponseEntity<>(new ApiResponse(true, Collections.singletonList(new ApiSingleResponse("Tweet Guardado.")),
                    HttpStatus.SC_OK), OK);
        } catch (JSONException e) {
            e.printStackTrace();
            return DEFAULT_ERROR_RESPONSE;
        }
    }

    @Override
    public ResponseEntity<ApiResponse> delete(Map<String, String> tweetMap) {
        String id = tweetMap.getOrDefault("id", "");
        if (id.isEmpty()) {
            return API_RESPONSE_BAD_REQUEST;
        }
        Tweet tweet = tweetRepository.findOneById(id).orElse(null);
        if (tweet == null) {
            return new ResponseEntity<>(new ApiResponse(false, Collections.singletonList(new ApiSingleResponse("Tweet No encontrado.")),
                    HttpStatus.SC_NOT_FOUND), NOT_FOUND);
        }
        tweetRepository.delete(tweet);
        return new ResponseEntity<>(new ApiResponse(true, Collections.singletonList(new ApiSingleResponse("Tweet Eliminado.")),
                HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getAll() {
        List<ApiData> apiData = new ArrayList<>(tweetRepository.findAll());
        return new ResponseEntity<>(new ApiResponse(true, apiData, HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getTweetById(String id) {
        if (id.isEmpty()) {
            return API_RESPONSE_BAD_REQUEST;
        }
        Tweet tweet = tweetRepository.findOneById(id).orElse(null);
        if (tweet == null) {
            return new ResponseEntity<>(new ApiResponse(false, Collections.singletonList(new ApiSingleResponse("Tweet No encontrado.")),
                    HttpStatus.SC_NOT_FOUND), NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse(true, Collections.singletonList(tweet),
                HttpStatus.SC_OK), OK);
    }
}
