package com.example.twitterapi.api.repository;

import com.example.twitterapi.api.bean.TestDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestDbRepository extends MongoRepository<TestDB, String> {

    long count();
}
