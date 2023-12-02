## YouTube Spring Cloud Starter
![Alpha](https://img.shields.io/badge/Release-Alpha-darkred)
[![Java CI](https://github.com/clue2solve/Youtube-Spring-Cloud-Starter/actions/workflows/ci.yml/badge.svg)](https://github.com/clue2solve/Youtube-Spring-Cloud-Starter/actions/workflows/ci.yml)
[![Known Vulnerabilities](https://snyk.io/test/github/clue2solve/Youtube-Spring-Cloud-Starter/badge.svg?style=plastic)](https://snyk.io/test/github/clue2solve/Youtube-Spring-Cloud-Starter)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

This project is a Spring Cloud Starter for the YouTube Data API. It provides a YouTubeService class that simplifies the process of fetching video metadata and captions from YouTube.

### Features
Fetch video metadata: The YouTubeService class provides a getVideo method that fetches metadata for a specific video. The metadata includes the video's title, description, view count, like count, and duration.

Fetch caption information: The YouTubeService class provides a getCaptionInfo method that fetches information about the captions for a specific video. The information includes the ID, language, track kind, and whether the caption is a draft or auto-synced.

### Usage
To use this starter in your Spring Cloud application, you need to:

Add a dependency to this starter in your pom.xml file.

```xml
<dependency>
    <groupId>io.clue2solve</groupId>
    <artifactId>Youtube-Spring-Cloud-Starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
#### Autowire an instance of the YouTubeService class in your application.

Call the getVideo method to fetch video metadata, and the getCaptionInfo method to fetch caption information.

Here's an example:
```Java
@Autowired
private YouTubeService youTubeService;

public void printVideoDetails(String videoId) throws IOException {
    Video video = youTubeService.getVideo(videoId);
    System.out.println("Title: " + video.getSnippet().getTitle());
    System.out.println("View count: " + video.getStatistics().getViewCount());

    List<CaptionInfo> captions = youTubeService.getCaptionInfo(videoId);
    for (CaptionInfo caption : captions) {
        System.out.println("Caption language: " + caption.getLanguage());
    }
}
```
### Requirements
- Java 8 or higher
- Spring Cloud
- YouTube Data API v3

### License
This project is licensed under the MIT License. See the LICENSE file for details.

