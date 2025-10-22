package dev.glabay.services;

import dev.glabay.dtos.UserCredentialsDto;
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
    private final UserProfileRepository userProfileRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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
        return RegistrationStatus.CREATED;
    }
}
