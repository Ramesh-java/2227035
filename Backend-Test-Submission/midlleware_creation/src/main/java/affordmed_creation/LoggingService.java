package affordmed_creation;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class LoggingService {

    private final RestTemplate restTemplate;
    private static final Logger LOGGER = Logger.getLogger(LoggingService.class.getName());

    // Define the API URL in your application.properties file
    @Value("${logging.api.url}")
    private String apiUrl;

    // Define the API Token in your application.properties file
    @Value("${logging.api.token}")
    String apiToken;
    public LoggingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Renaming the record field for JSON serialization
    public record LogRequestWithPackageFix(
            LogStack stack,
            LogLevel level,
            @JsonProperty("package") LogPackage aPackage, // Map aPackage to "package"
            String message
    ) {}


    public void log(LogStack stack, LogLevel level, LogPackage aPackage, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(apiToken);
        // The prompt mentions a "protected route", usually via an Authorization header
        headers.set("Authorization", "Bearer " + apiToken); // Or your specific auth scheme

        LogRequestWithPackageFix requestBody = new LogRequestWithPackageFix(stack, level, aPackage, message);
        HttpEntity<LogRequestWithPackageFix> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            LogResponse response = restTemplate.postForObject(apiUrl, requestEntity, LogResponse.class);
            if (response != null) {
                LOGGER.info("Log sent successfully. Log ID: " + response.logID());
            }
        } catch (RestClientException e) {
            // Handle exceptions (e.g., API is down, network error)
            LOGGER.severe("Failed to send log to external service: " + e.getMessage());
        }
    }
}