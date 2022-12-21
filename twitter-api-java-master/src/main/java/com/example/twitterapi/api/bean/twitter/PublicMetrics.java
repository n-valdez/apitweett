package com.example.twitterapi.api.bean.twitter;

import com.google.gson.annotations.SerializedName;

public class PublicMetrics {
    @SerializedName("followers_count")
    private long followersCount;

    @SerializedName("following_count")
    private long followingCount;

    @SerializedName("tweet_count")
    private long tweetCount;

    @SerializedName("listed_Count")
    private long listedCount;

    public long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }

    public long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(long followingCount) {
        this.followingCount = followingCount;
    }

    public long getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(long tweetCount) {
        this.tweetCount = tweetCount;
    }

    public long getListedCount() {
        return listedCount;
    }

    public void setListedCount(long listedCount) {
        this.listedCount = listedCount;
    }
}
