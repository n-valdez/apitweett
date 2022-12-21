package com.example.twitterapi.api.services;

import com.example.twitterapi.api.bean.ApiData;
import com.example.twitterapi.api.bean.ApiSingleResponse;
import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.bean.twitter.HashtagCount;
import com.example.twitterapi.api.command.Command;
import com.example.twitterapi.api.command.CommandUrl;
import com.example.twitterapi.api.repository.HashtagCountRepository;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.twitterapi.api.helper.ResponseUtils.API_RESPONSE_BAD_REQUEST;
import static org.springframework.http.HttpStatus.*;

@Service
public class HashtagServiceImpl implements HashtagService {


    private final Command command;
    private final HashtagCountRepository hashtagCountRepository;

    @Autowired
    public HashtagServiceImpl(Command command, HashtagCountRepository hashtagCountRepository) {
        this.command = command;
        this.hashtagCountRepository = hashtagCountRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> saveQuantityToday(Map<String, String> hashtagMap) throws IOException, JSONException {
        String hashtag = hashtagMap.getOrDefault("hashtag", "");
        if (hashtag.isEmpty()) {
            return API_RESPONSE_BAD_REQUEST;
        }
        String tweetsTwQuery = URLEncoder.encode(String.format("#%s", hashtag), "UTF-8");
        HttpGet tweetsTw = new HttpGet(String.format(CommandUrl.TWEETS_COUNT, tweetsTwQuery,
                Instant.now().truncatedTo(ChronoUnit.DAYS).toString()));
        String tweetsTwResp = command.executeHttpMethod(tweetsTw);
        JSONObject twTweetsJson = new JSONObject(tweetsTwResp);
        int totalTweetsTodayHashtag = twTweetsJson.getJSONObject("meta").getInt("total_tweet_count");
        LocalDateTime ldt = LocalDateTime.now();
        String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt);
        HashtagCount newHashtag = new HashtagCount();
        newHashtag.setHashtag(hashtag);
        newHashtag.setCount(totalTweetsTodayHashtag);
        newHashtag.setDate(currentDate);
        hashtagCountRepository.findOneByHashtagAndDate(hashtag, currentDate)
                .ifPresent(hashtagObj -> newHashtag.setId(hashtagObj.getId()));
        hashtagCountRepository.save(newHashtag);
        return new ResponseEntity<>(new ApiResponse(true, Collections.singletonList(new ApiSingleResponse("Hashtag Guardado.")),
                HttpStatus.SC_OK), OK);
    }

    @Override
    public ResponseEntity<ApiResponse> getHashtagQuantity(String date) {
        LocalDateTime localDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            localDate = LocalDate.parse(date, formatter).atStartOfDay();
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return API_RESPONSE_BAD_REQUEST;
        }
        String dateFormatted = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<ApiData> apiDataList = new ArrayList<>(hashtagCountRepository.findAllByDateOrderByCountDesc(dateFormatted));
        return new ResponseEntity<>(new ApiResponse(true, apiDataList, HttpStatus.SC_OK), OK);
    }
}
