package dev.glabay.feaures.users;

import dev.glabay.logging.MidnightLogger;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullUnmarked;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Glabay | Glabay-Studios
 * @project backend
 * @social Discord: Glabay
 * @since 2025-10-21
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger log = MidnightLogger.getLogger(CustomUserDetailsService.class);

    private final UserProfileRepository userProfileRepository;

    @Override
    @NullUnmarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading UserDetails for username: {}", username);
        if (userProfileRepository.existsByEmail(username)) {
            var cachedUser = userProfileRepository.findByEmailIgnoreCase(username);
            log.info("UserDetails for username: {}", cachedUser);
            return cachedUser
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        }
        log.debug("No user found for for email: {}", username);
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
