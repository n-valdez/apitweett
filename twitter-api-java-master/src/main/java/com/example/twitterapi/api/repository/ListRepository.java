package com.example.twitterapi.api.repository;

import com.example.twitterapi.api.bean.twitter.List;
import com.example.twitterapi.api.bean.twitter.Space;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ListRepository extends MongoRepository<List, String> {
    Optional<List> findOneById(String id);
}
