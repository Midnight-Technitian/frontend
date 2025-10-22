package dev.glabay.services;

import dev.glabay.dtos.UserCredentialsDto;
import dev.glabay.models.request.RegistrationStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @author Glabay | Glabay-Studios
 * @project frontend
 * @social Discord: Glabay
 * @since 2025-10-21
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RestClient restClient;

    public RegistrationStatus registerUser(UserCredentialsDto request) {
        return restClient.post()
            .uri("/v1/registrar")
            .body(request)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<RegistrationStatus>() {})
            .getBody();
    }
}
