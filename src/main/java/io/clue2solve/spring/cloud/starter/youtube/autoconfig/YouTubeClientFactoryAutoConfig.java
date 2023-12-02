package io.clue2solve.spring.cloud.starter.youtube.autoconfig;

import io.clue2solve.spring.cloud.starter.youtube.client.YouTubeClientFactory;
import io.clue2solve.spring.cloud.starter.youtube.client.impl.ApiKeyYouTubeClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YouTubeClientFactoryAutoConfig {

    @Value("${youtube.apiKey}")
    private String apiKey;

    @Bean
    @ConditionalOnProperty(name = "youtube.apiKey")
    public YouTubeClientFactory youTubeClientFactory() {
        return new ApiKeyYouTubeClientFactory(apiKey);
    }
}