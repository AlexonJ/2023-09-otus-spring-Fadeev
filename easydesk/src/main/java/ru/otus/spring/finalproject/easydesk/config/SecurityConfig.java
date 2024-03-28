package ru.otus.spring.finalproject.easydesk.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    private static final String TICKET_API_PATH = "/tickets";
    private static final String COMMENT_API_PATH = "/comments";

    private static final String ATTACHMENT_API_PATH = "/attachments";
    @Bean
    public SecurityFilterChain securityFilterChainForApi(HttpSecurity httpSecurity) throws Exception {
        String basePath = settingsProvider.getBasePath();
        String ticketApiFullPath = basePath + TICKET_API_PATH + "/**";
        String commentApiFullPath = basePath + COMMENT_API_PATH + "/**";
        String attachmentApiFullPath = basePath + ATTACHMENT_API_PATH + "/**";
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorizationManager -> authorizationManager
                                .requestMatchers("error", "h2-console/**", "swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.GET, ticketApiFullPath).hasAuthority("READ_TICKETS")
                                .requestMatchers(HttpMethod.POST, ticketApiFullPath).hasAuthority("CREATE_TICKETS")
                                .requestMatchers(HttpMethod.DELETE, ticketApiFullPath).hasAuthority("DELETE_TICKETS")
                                .requestMatchers(HttpMethod.GET, commentApiFullPath).hasAuthority("READ_COMMENTS")
                                .requestMatchers(HttpMethod.POST, commentApiFullPath).hasAuthority("CREATE_COMMENTS")
                                .requestMatchers(HttpMethod.DELETE, commentApiFullPath).hasAuthority("DELETE_COMMENTS")
                                .requestMatchers(HttpMethod.GET, attachmentApiFullPath).hasAuthority("READ_ATTACHMENTS")
                                .requestMatchers(HttpMethod.POST, attachmentApiFullPath).hasAuthority("CREATE_ATTACHMENTS")
                                .requestMatchers(HttpMethod.DELETE, attachmentApiFullPath).hasAuthority("DELETE_ATTACHMENTS")
//                        .requestMatchers("/index.html").authenticated()
                                .anyRequest().authenticated()
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