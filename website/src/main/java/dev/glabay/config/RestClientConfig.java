package dev.glabay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * @author Glabay | Glabay-Studios
 * @project GlabTech
 * @social Discord: Glabay
 * @since 2025-10-17
 */
@Configuration
public class RestClientConfig {

    @Value("${backend.api.token}")
    private String API_TOKEN;

    @Bean
    public RestClient getRestClient(@Value("${backend.api.url}") String API_URL) {
        return RestClient.builder()
            .baseUrl(API_URL)
            .requestFactory(new JdkClientHttpRequestFactory())
            .defaultHeader("Authorization", "Bearer ".concat(API_TOKEN))
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
