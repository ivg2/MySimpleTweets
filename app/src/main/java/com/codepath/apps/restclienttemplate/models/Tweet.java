package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {

    public String body;
    public long uid; //database ID for the tweet
    public String createdAt;
    public User user;
    public Long favorite_count;
    public Boolean isFavorited = false;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // acquire the values from the JSON file
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.isFavorited = jsonObject.optBoolean("favorited");
        tweet.favorite_count = jsonObject.optLong("favorite_count");

        return tweet;
    }
}
