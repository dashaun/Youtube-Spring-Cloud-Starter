package io.clue2solve.spring.cloud.starter.youtube;

import com.google.api.services.youtube.model.ThumbnailDetails;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;

import ch.qos.logback.classic.Logger;
import io.clue2solve.spring.cloud.starter.youtube.services.YouTubeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestInit.class)
@ActiveProfiles("authorized")
public class YouTubeServiceIntegrationTest {
    Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(YouTubeServiceIntegrationTest.class);
    @Autowired
    private YouTubeService youTubeService;

    @Test
    public void getVideoTest() throws Exception {
        Video video = youTubeService.getVideo("9SGDpanrc8U"); // replace with a valid video ID
        // Get the video snippet
        VideoSnippet snippet = video.getSnippet();
        if (snippet != null) {
            String title = snippet.getTitle();
            String description = snippet.getDescription();
            ThumbnailDetails thumbnails = snippet.getThumbnails();
            logger.info("Title: {}", title);
            logger.info("Description: {}", description);
            // You can get default, medium, high, standard, maxres thumbnails from ThumbnailDetails
        }
        assertNotNull(video);
    }
}