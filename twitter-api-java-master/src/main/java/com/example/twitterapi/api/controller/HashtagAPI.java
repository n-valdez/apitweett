package com.example.twitterapi.api.controller;

import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.services.HashtagService;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/hashtag")
public class HashtagAPI {
    private final HashtagService hashtagService;

    @Autowired
    public HashtagAPI(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

    @PostMapping("/save_quantity_today")
    public ResponseEntity<ApiResponse> saveQuantityToday(@RequestBody Map<String, String> hashtagMap) throws IOException, JSONException {
        return hashtagService.saveQuantityToday(hashtagMap);
    }

    @GetMapping("/get_hashtag_quantity_desc")
    public ResponseEntity<ApiResponse> getHashtagQuantity(@RequestParam(value = "date") String date
    ) {
        return hashtagService.getHashtagQuantity(date);
    }


}
