package com.example.twitterapi.api.repository;

import com.example.twitterapi.api.bean.twitter.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TweetRepository extends MongoRepository<Tweet, String> {
    Optional<Tweet> findOneById(String id);
}
