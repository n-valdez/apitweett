package com.example.twitterapi.api.controller;

import com.example.twitterapi.api.bean.ResponseConnectionStatus;
import com.example.twitterapi.api.repository.TestDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusAPI {
    private final TestDbRepository testDbRepository;


    @Value("${apiVersion}")
    private String apiVersion;

    @Autowired
    public StatusAPI(TestDbRepository testDbRepository) {
        this.testDbRepository = testDbRepository;
    }


    @GetMapping("")
    public ResponseConnectionStatus getConnectionStatus() {
        ResponseConnectionStatus r = new ResponseConnectionStatus();
        r.setBackendConnection(true);
        r.setBackendVersion(apiVersion);
        r.setDataBaseConnection(testDbRepository.count() > 0);
        return r;
    }
}
