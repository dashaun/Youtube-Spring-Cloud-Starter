package io.clue2solve.spring.cloud.starter.youtube.client;

import com.google.api.services.youtube.YouTube;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface YouTubeClientFactory {

	YouTube createYouTubeClient() throws GeneralSecurityException, IOException;

}