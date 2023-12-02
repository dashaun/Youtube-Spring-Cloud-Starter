package io.clue2solve.spring.cloud.starter.youtube.client.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import io.clue2solve.spring.cloud.starter.youtube.client.YouTubeClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@ConditionalOnProperty(name = "youtube.apiKey")
public class ApiKeyYouTubeClientFactory implements YouTubeClientFactory {

	private final String apiKey;

	public ApiKeyYouTubeClientFactory(@Value("${youtube.apiKey}") String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public YouTube createYouTubeClient() throws GeneralSecurityException, IOException {
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

		return new YouTube.Builder(httpTransport, jsonFactory, null).setApplicationName("youtube-starter")
			.setYouTubeRequestInitializer(new YouTubeRequestInitializer(apiKey))
			.build();
	}

}