package com.example.twitter_backend.util.mapper;

import com.example.twitter_backend.dto.response.TweetResponse;
import com.example.twitter_backend.entity.Like;
import com.example.twitter_backend.entity.Retweet;
import com.example.twitter_backend.entity.Tweet;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TweetMapper {

    public TweetResponse toResponse(Tweet tweet){
        return toResponse(tweet, null);
    }

    public TweetResponse toResponse(Tweet tweet, Long viewerId){
        if(tweet == null){
            return null;
        }
        TweetResponse response = new TweetResponse();
        response.setId(tweet.getId());
        response.setContent(tweet.getContent());
        response.setCreatedAt(tweet.getCreatedAt());
        response.setCommentCount(tweet.getComments() == null ? 0 : tweet.getComments().size());
        response.setLikeCount(tweet.getLikes() == null ? 0 : tweet.getLikes().size());
        response.setRetweetCount(tweet.getRetweets() == null ? 0 : tweet.getRetweets().size());

        if(tweet.getUser() != null){
            response.setUserId(tweet.getUser().getId());
            response.setUserNickName(tweet.getUser().getNickName());
        }
        boolean likedByViewer = false;
        boolean retweetedByViewer = false;
        if (viewerId != null) {
            if (tweet.getLikes() != null) {
                likedByViewer = tweet.getLikes()
                        .stream()
                        .map(Like::getUser)
                        .filter(user -> user != null)
                        .anyMatch(user -> viewerId.equals(user.getId()));
            }
            if (tweet.getRetweets() != null) {
                retweetedByViewer = tweet.getRetweets()
                        .stream()
                        .map(Retweet::getUser)
                        .filter(user -> user != null)
                        .anyMatch(user -> viewerId.equals(user.getId()));
            }
        }
        response.setLikedByViewer(likedByViewer);
        response.setRetweetedByViewer(retweetedByViewer);
        return response;
        }

        public List<TweetResponse> toResponseList(List<Tweet> tweets){
            return toResponseList(tweets, null);
        }

        public List<TweetResponse> toResponseList(List<Tweet> tweets, Long viewerId){
        if (tweets == null || tweets.isEmpty()){
            return Collections.emptyList();
        }
        return tweets.stream()
                .map(tweet -> this.toResponse(tweet, viewerId))
                .collect(Collectors.toList());
        }
}
