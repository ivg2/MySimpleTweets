package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    List<Tweet> tweets;
    Context context;

    //pass in Tweets Array
    public TweetAdapter (List<Tweet> tweets) {
        this.tweets = tweets;
    }

    //inflate layout for each Tweet and pass into ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder view = new ViewHolder(tweetView);
        return view;
    }

    //bind values based on index of element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);

        //populate the views according to the position
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .into(holder.ivProfileImage);

        holder.tvTime.setText(getRelativeTimeAgo(tweet.createdAt));
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfileImage;
        public TextView tvBody;
        public TextView tvUsername;
        public TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvTime = itemView.findViewById(R.id.tvTime);
            itemView.setOnClickListener(this);
        }

        //when a user clicks on a row show the TwitterDetailActivity
        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Tweet tweet = tweets.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, TweetDetail.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                // show the activity
                context.startActivity(intent);
            }
        }
    }
}
