package com.example.twitterapi.api.services;

import com.example.twitterapi.api.bean.ApiResponse;
import org.codehaus.jettison.json.JSONException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public interface HashtagService {
    ResponseEntity<ApiResponse> saveQuantityToday(Map<String, String> hashtagMap) throws IOException, JSONException;

    ResponseEntity<ApiResponse> getHashtagQuantity(String date);
}
