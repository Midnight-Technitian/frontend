package dev.glabay.feaures.users;

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Glabay | Glabay-Studios
 * @project GlabTech
 * @social Discord: Glabay
 * @since 2024-11-30
 */
public record CustomUserDetails(
    UserProfile userProfile
) implements UserDetails {

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userProfile.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return userProfile.getEncryptedPassword();
    }

    @Override
    @NullMarked
    public String getUsername() {
        return userProfile.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
