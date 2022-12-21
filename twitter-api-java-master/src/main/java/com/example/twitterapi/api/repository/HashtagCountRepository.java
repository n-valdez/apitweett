package com.example.twitterapi.api.repository;

import com.example.twitterapi.api.bean.twitter.HashtagCount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface HashtagCountRepository extends MongoRepository<HashtagCount, String> {

    Optional<HashtagCount> findOneByHashtagAndDate(String hashtag, String date);

    List<HashtagCount> findAllByDateOrderByCountDesc(String date);

}
