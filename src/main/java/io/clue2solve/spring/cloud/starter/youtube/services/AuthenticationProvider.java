package io.clue2solve.spring.cloud.starter.youtube.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class AuthenticationProvider {

	@Value("${credentials.json:#{null}}")
	private String credentialsJson;

	public GoogleCredentials getCredentials() throws IOException {
		if (credentialsJson != null && !credentialsJson.isEmpty()) {
			InputStream credentialsStream = new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8));
			return GoogleCredentials.fromStream(credentialsStream);
		}
		else {
			throw new RuntimeException("Failed to load credentials");
		}
	}

}