package com.example.twitterapi.api.repository;

import com.example.twitterapi.api.bean.twitter.Space;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpaceRepository extends MongoRepository<Space, String> {
    Optional<Space> findOneById(String id);
}
