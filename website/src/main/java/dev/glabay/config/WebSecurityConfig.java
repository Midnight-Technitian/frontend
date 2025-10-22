package dev.glabay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Glabay | Glabay-Studios
 * @project frontend
 * @social Discord: Glabay
 * @since 2025-10-21
 */
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/home",
                    "/index",
                    "/error",
                    "/auth/login",
                    "/auth/register",
                    "/css/**",
                    "/webjars/**"
                ).permitAll()
                // User-Relate (requires a logged-in user to have a ROLE_USER to access)
                .requestMatchers(
                    "/dashboard/**"
                ).hasRole("USER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.
                loginPage("/auth//login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
        return http.build();
    }
}
