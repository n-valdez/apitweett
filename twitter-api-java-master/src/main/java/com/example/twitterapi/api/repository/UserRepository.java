package com.example.twitterapi.api.repository;

import com.example.twitterapi.api.bean.twitter.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findOneByUsername(String username);

    List<User> findAllByOrderByPublicMetricsDesc();

    List<User> findAllByOrderByName();
}
