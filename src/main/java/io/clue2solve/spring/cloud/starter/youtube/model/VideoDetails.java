package io.clue2solve.spring.cloud.starter.youtube.model;

import java.math.BigInteger;
import java.util.List;

/**
 * Represents the details of a YouTube video.
 * This is extracted from the YouTube API. See https://developers.google.com/youtube/v3/docs/videos
 */
public record VideoDetails(String id, String title, String description, BigInteger viewCount, BigInteger likeCount,
                           String duration, int numberOfCaptions, List<String> captionLanguages) {
}