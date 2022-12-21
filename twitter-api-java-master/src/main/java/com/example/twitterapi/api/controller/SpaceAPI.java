package com.example.twitterapi.api.controller;

import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.services.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/space")
public class SpaceAPI {


    private final SpaceService spaceService;

    @Autowired
    public SpaceAPI(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody Map<String, String> userMap) throws IOException {
        return spaceService.save(userMap);
    }

    @GetMapping("/get_all")
    public ResponseEntity<ApiResponse> getAll() {
        return spaceService.getAll();
    }

}

