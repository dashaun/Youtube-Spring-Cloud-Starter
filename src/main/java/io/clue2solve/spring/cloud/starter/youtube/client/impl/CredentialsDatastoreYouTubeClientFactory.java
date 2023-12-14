package io.clue2solve.spring.cloud.starter.youtube.client.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.common.collect.Lists;
import io.clue2solve.spring.cloud.starter.youtube.client.YouTubeClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;

@Component
@ConditionalOnProperty(name = "youtube.credentialsDirectory")
public class CredentialsDatastoreYouTubeClientFactory implements YouTubeClientFactory {

	private final String credentialsDirectory;

	public CredentialsDatastoreYouTubeClientFactory(
			@Value("${youtube.credentialsDirectory}") String credentialsDirectory) {
		this.credentialsDirectory = credentialsDirectory;
	}

	@Override
	public YouTube createYouTubeClient() throws GeneralSecurityException, IOException {
		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.force-ssl");
		Credential credential = Auth.authorize(scopes, "captions", credentialsDirectory);
		return new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
			.setApplicationName("youtube-starter")
			.build();
	}

}

class Auth {

	/**
	 * Define a global instance of the HTTP transport.
	 */
	public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/**
	 * Define a global instance of the JSON factory.
	 */
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();

	/**
	 * Authorizes the installed application to access user's protected data.
	 * @param scopes list of scopes needed to run youtube upload.
	 * @param credentialDatastore name of the credential datastore to cache OAuth tokens
	 */
	public static Credential authorize(List<String> scopes, String credentialDatastore, String credentialsDirectory)
			throws IOException {

		// Load client secrets.
		Reader clientSecretReader = new InputStreamReader(
				Objects.requireNonNull(Auth.class.getResourceAsStream("/client_secrets.json")));
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);

		// Checks that the defaults have been replaced (Default = "Enter X here").
		if (clientSecrets.getDetails().getClientId().startsWith("Enter")
				|| clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
			System.out.println(
					"Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential "
							+ "into src/main/resources/client_secrets.json");
			System.exit(1);
		}

		// This creates the credentials datastore at
		// ~/.oauth-credentials/${credentialDatastore}
		FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(
				new File(System.getProperty("user.home") + "/" + credentialsDirectory));
		DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, scopes)
			.setCredentialDataStore(datastore)
			.build();

		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver.Builder().setPort(8080).build()).authorize("user");

	}

}
