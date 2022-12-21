package com.example.twitterapi.api.controller;

import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.services.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/list")
public class ListAPI {


    private final ListService listService;

    @Autowired
    public ListAPI(ListService listService) {
        this.listService = listService;
    }

    @PostMapping("/save_by_username")
    public ResponseEntity<ApiResponse> saveListsByUsername(@RequestBody Map<String, String> userMap) throws IOException {
        return listService.saveByUsername(userMap);
    }

    @GetMapping("/get_all")
    public ResponseEntity<ApiResponse> getAll() {
        return listService.getAll();
    }

}
