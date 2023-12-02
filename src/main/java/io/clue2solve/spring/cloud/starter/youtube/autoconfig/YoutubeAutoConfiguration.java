package io.clue2solve.spring.cloud.starter.youtube.autoconfig;


import com.google.api.services.youtube.YouTube;
import io.clue2solve.spring.cloud.starter.youtube.client.YouTubeClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
@Configuration
public class YoutubeAutoConfiguration {

    private final YouTubeClientFactory clientFactory;

    @Autowired
    public YoutubeAutoConfiguration(YouTubeClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Bean
    public YouTube youTube() throws GeneralSecurityException, IOException {
        return clientFactory.createYouTubeClient();
    }
}