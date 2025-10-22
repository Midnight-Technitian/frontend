package dev.glabay.services;

import dev.glabay.dtos.CustomerDto;
import dev.glabay.dtos.UserCredentialsDto;
import dev.glabay.dtos.UserProfileDto;
import dev.glabay.feaures.users.UserProfile;
import dev.glabay.feaures.users.UserProfileRepository;
import dev.glabay.models.request.RegistrationStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
/**
 * @author Glabay | Glabay-Studios
 * @project frontend
 * @social Discord: Glabay
 * @since 2025-10-21
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserProfileRepository userProfileRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RestClient restClient;

    public RegistrationStatus registerUser(UserCredentialsDto request) {
        var exists = userProfileRepository.existsByEmail(request.email());
        if (exists)
            return RegistrationStatus.ALREADY_EXISTS;

        if (request.password().isEmpty() || request.password().isBlank() ||
            request.rePassword().isEmpty() || request.rePassword().isBlank() ||
            !request.password().equals(request.rePassword())
        ) return RegistrationStatus.INVALID_CREDENTIALS;

        var newUser = new UserProfile();
            newUser.setEmail(request.email());
            newUser.setFirstName(request.firstName());
            newUser.setLastName(request.lastName());
            newUser.setContactNumber(request.contactNumber());
            newUser.setEncryptedPassword(passwordEncoder.encode(request.password()));
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setUpdatedAt(LocalDateTime.now());

        var cached = userProfileRepository.save(newUser);
        if (cached == null)
            return RegistrationStatus.FAILED;
        createNewCustomer(cached);
        return RegistrationStatus.CREATED;
    }

    private void createNewCustomer(UserProfile newUser) {
        var dto = new UserProfileDto(
            newUser.getEmail(),
            newUser.getFirstName(),
            newUser.getLastName(),
            newUser.getContactNumber(),
            newUser.getCreatedAt(),
            newUser.getUpdatedAt()
        );
        // TODO: Create a Kafka event for creating a new customer
        // for now we will just make a POST to the backend API
        var reply = restClient.post()
            .uri("/v1/customers")
            .body(dto)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<CustomerDto>() {});

        var status = reply.getStatusCode();

        if (status.is2xxSuccessful()) {
            var customerDto = reply.getBody();
            // TODO: maybe we can add a little welcome email to be sent to the customer
            logger.info("Customer created successfully\n{}", customerDto);
        }
        else {
            logger.error("Failed to create customer");
        }
    }
}
