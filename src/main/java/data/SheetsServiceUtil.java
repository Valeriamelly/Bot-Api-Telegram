package data;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class SheetsServiceUtil {
    private static final String APPLICATION_NAME = "Google Sheets Bot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Creates an authorized Credential object.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    public static GoogleCredentials getCredentials() throws IOException {
        // Load client secrets.
        FileInputStream credentialStream = new FileInputStream("D:\\PROGRAMACION\\API\\credentials.json");
        return GoogleCredentials.fromStream(credentialStream)
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
    }

    /**
     * Build and return a Sheets API client service.
     * @return An authorized Sheets API client service
     * @throws IOException If the credentials.json file cannot be found.
     */
    public static Sheets getSheetsService() throws IOException {
        GoogleCredentials credentials = getCredentials();
        return new Sheets.Builder(new NetHttpTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


}