package io.clue2solve.spring.cloud.starter.youtube.model;

/**
 * Represents the details of a YouTube video Caption. This is extracted from the YouTube
 * API. See https://developers.google.com/youtube/v3/docs/captions
 */
public record CaptionInfo(String id, String language, String trackKind, boolean isDraft, boolean isAutoSynced) {
}