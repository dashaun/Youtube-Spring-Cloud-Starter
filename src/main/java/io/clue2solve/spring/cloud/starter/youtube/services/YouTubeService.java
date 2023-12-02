package io.clue2solve.spring.cloud.starter.youtube.services;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YouTubeService {

	private final YouTube youtube;

	@Autowired
	public YouTubeService(YouTube youtube) {
		this.youtube = youtube;
	}

	// Fetch video metadata
	public Video getVideo(String videoId) throws IOException {
		YouTube.Videos.List request = this.youtube.videos().list("snippet,contentDetails,statistics");
		VideoListResponse response = request.setId(videoId).execute();
		return response.getItems().get(0);
	}

	public VideoDetails getVideoDetails(String videoId) throws IOException {
		YouTube.Videos.List request = this.youtube.videos().list("snippet,contentDetails,statistics");
		VideoListResponse response = request.setId(videoId).execute();
		Video video = response.getItems().get(0);

		VideoSnippet snippet = video.getSnippet();
		String title = snippet != null ? snippet.getTitle() : null;
		String description = snippet != null ? snippet.getDescription() : null;

		VideoStatistics stats = video.getStatistics();
		BigInteger viewCount = stats != null ? stats.getViewCount() : null;
		BigInteger likeCount = stats != null ? stats.getLikeCount() : null;

		VideoContentDetails contentDetails = video.getContentDetails();
		String duration = contentDetails != null ? contentDetails.getDuration() : null;

		// Get captions
		YouTube.Captions.List captionRequest = this.youtube.captions().list("snippet", videoId);
		CaptionListResponse captionResponse = captionRequest.setVideoId(videoId).execute();
		List<Caption> captions = captionResponse.getItems();
		int numberOfCaptions = captions.size();
		List<String> captionLanguages = captions.stream()
			.map(caption -> caption.getSnippet().getLanguage())
			.collect(Collectors.toList());

		return new VideoDetails(video.getId(), title, description, viewCount, likeCount, duration, numberOfCaptions,
				captionLanguages);
	}

	public List<CaptionInfo> getCaptionInfo(String videoId) throws IOException {
		CaptionListResponse captionResponse = this.youtube.captions().list("snippet", videoId).execute();
		List<Caption> captions = captionResponse.getItems();

		return captions.stream()
			.map(caption -> new CaptionInfo(caption.getId(), caption.getSnippet().getLanguage(),
					caption.getSnippet().getTrackKind(), caption.getSnippet().getIsDraft(),
					caption.getSnippet().getIsAutoSynced()))
			.collect(Collectors.toList());
	}

	public record VideoDetails(String id, String title, String description, BigInteger viewCount, BigInteger likeCount,
			String duration, int numberOfCaptions, List<String> captionLanguages) {
	}

	public record CaptionInfo(String id, String language, String trackKind, boolean isDraft, boolean isAutoSynced) {
	}

}