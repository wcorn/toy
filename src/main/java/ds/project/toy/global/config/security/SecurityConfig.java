package ds.project.toy.global.config.security;

import ds.project.toy.global.config.security.jwt.JwtAuthenticationEntryPoint;
import ds.project.toy.global.config.security.oauth.handler.OAuth2AuthenticationFailureHandler;
import ds.project.toy.global.config.security.oauth.handler.OAuth2AuthenticationSuccessHandler;
import ds.project.toy.global.config.security.oauth.service.OAuth2UserServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${security.cors-urls}")
    private final List<String> corsUrls;

    @Bean
    public SecurityFilterChain filterChain(
        HttpSecurity http, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
        OAuth2UserServiceImpl oAuth2UserService,
        OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
        OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler)
        throws Exception {
        http
            .cors(
                httpSecurityCorsConfigurer -> corsConfigurationSource())
            .csrf(
                AbstractHttpConfigurer::disable)
            .sessionManagement(
                httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                    .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .oauth2Login(
                oauth -> oauth
                    .userInfoEndpoint(config -> config.userService(oAuth2UserService))
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
            )
            .exceptionHandling(config -> config
                .defaultAuthenticationEntryPointFor(
                    jwtAuthenticationEntryPoint,
                    request -> !request.getRequestURI().startsWith("/login")
                )
            )
            .authorizeHttpRequests(request -> request
                .requestMatchers("/actuator/**", "/swagger-ui/**",
                    "/swagger-resources/**", "/api-docs/**").permitAll()
                .requestMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
            )

        ;
        return http.build();
    }

    // CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(corsUrls);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}