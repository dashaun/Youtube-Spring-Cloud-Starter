package io.clue2solve.spring.cloud.starter.youtube.autoconfig;

import io.clue2solve.spring.cloud.starter.youtube.services.AuthenticationProvider;
import io.clue2solve.spring.cloud.starter.youtube.services.YouTubeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class YoutubeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public YouTubeService youTubeService(AuthenticationProvider authenticationProvider) throws GeneralSecurityException, IOException {
        return new YouTubeService(authenticationProvider);
    }
}