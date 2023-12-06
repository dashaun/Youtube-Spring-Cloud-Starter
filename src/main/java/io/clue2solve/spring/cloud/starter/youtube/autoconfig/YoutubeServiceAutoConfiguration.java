package io.clue2solve.spring.cloud.starter.youtube.autoconfig;

import com.google.api.services.youtube.YouTube;
import io.clue2solve.spring.cloud.starter.youtube.services.YouTubeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeServiceAutoConfiguration {

	@Bean
	public YouTubeService youTubeService(YouTube youTube) {
		return new YouTubeService(youTube);
	}

	// Other @Bean methods...

}
