package io.clue2solve.spring.cloud.starter.youtube.services;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import io.clue2solve.spring.cloud.starter.youtube.model.CaptionInfo;
import io.clue2solve.spring.cloud.starter.youtube.model.VideoDetails;

@Service
public class YouTubeService {

	private final YouTube youtube;

	@Autowired
	public YouTubeService(YouTube youtube) {
		this.youtube = youtube;
	}

	// Fetch video metadata
	public Video getVideo(String videoId) throws IOException {
		YouTube.Videos.List request = this.youtube.videos().list(Arrays.asList("snippet", "contentDetails"));
		VideoListResponse response = request.setId(Collections.singletonList(videoId)).execute();
		return response.getItems().get(0);
	}

	public VideoDetails getVideoDetails(String videoId) throws IOException {
		YouTube.Videos.List request = this.youtube.videos().list(Arrays.asList("snippet,contentDetails,statistics"));
		VideoListResponse response = request.setId(Collections.singletonList(videoId)).execute();
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
		YouTube.Captions.List captionRequest = this.youtube.captions()
			.list(Collections.singletonList("snippet"), videoId);
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
		CaptionListResponse captionResponse = this.youtube.captions()
			.list(Collections.singletonList("snippet"), videoId)
			.execute();
		List<Caption> captions = captionResponse.getItems();

		return captions.stream()
			.map(caption -> new CaptionInfo(caption.getId(), caption.getSnippet().getLanguage(),
					caption.getSnippet().getTrackKind(), caption.getSnippet().getIsDraft(),
					caption.getSnippet().getIsAutoSynced()))
			.collect(Collectors.toList());
	}

}