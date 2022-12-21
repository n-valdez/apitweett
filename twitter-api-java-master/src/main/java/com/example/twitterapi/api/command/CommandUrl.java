package com.example.twitterapi.api.command;

public class CommandUrl {

    public static final String BASE_URL = "https://api.twitter.com/2";
    public static final String SEARCH_USERS = BASE_URL + "/users/by/username/%s";
    public static final String USER_TWEETS = BASE_URL + "/users/%s/tweets";
    public static final String USER_FOLLOWERS = BASE_URL + "/users/%s/followers";
    public static final String USER_LIKED_TWEET = BASE_URL + "/users/%s/liked_tweets";
    public static final String USER_MENTIONS = BASE_URL + "/users/%s/mentions";
    public static final String TWEETS_COUNT = BASE_URL + "/tweets/counts/recent?query=%s&start_time=%s";
    public static final String SEARCH_SPACES = BASE_URL + "/spaces/search?query=%s&space.fields=title";
    public static final String LISTS_BY_USER_ID = BASE_URL + "/users/%s/owned_lists";
    public static final String GET_TWEET_BY_ID = BASE_URL + "/tweets/%s";
}
