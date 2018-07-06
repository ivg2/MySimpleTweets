package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class tweetDetail extends AppCompatActivity {
    Tweet tweet;

    TextView profileName;
    TextView profileUserName;
    TextView description;
    ImageView profileImage;

    //TODO favorites and retweet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_tweet);

        profileName = findViewById(R.id.name);
        profileUserName = findViewById(R.id.screenName);
        description = findViewById(R.id.description);
        profileImage = findViewById(R.id.imageView);

        //unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        profileName.setText(tweet.user.name);
        profileUserName.setText(tweet.user.screenName);
        description.setText(tweet.body);

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .into(profileImage);

    }
}
