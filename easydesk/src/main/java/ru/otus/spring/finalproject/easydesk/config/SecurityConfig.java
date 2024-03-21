package ru.otus.spring.finalproject.easydesk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.otus.spring.finalproject.easydesk.repositories.SettingsProvider;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final SettingsProvider settingsProvider;

    @Bean
    public SecurityFilterChain securityFilterChainForApi(HttpSecurity httpSecurity) throws Exception {
        String basePath = settingsProvider.getBasePath();

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorizationManager -> authorizationManager
                        .requestMatchers("/**", "error", "h2-console/**", "swagger-ui/**").permitAll()
//                        .requestMatchers("/index.html").authenticated()
                        .anyRequest().permitAll()
//                        .anyRequest().denyAll()
                        )
                .userDetailsService(userDetailsService)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        // Allow framing to enable H2 console in an iframe
        httpSecurity.headers().frameOptions().disable();

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}