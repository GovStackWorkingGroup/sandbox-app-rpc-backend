package global.govstack.rpcbackend.configuration;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtAuthFilter authFilter;

  private final UserDetailsService userDetailsService;

  public SecurityConfig(JwtAuthFilter authFilter, UserDetailsService userDetailsService) {
    this.authFilter = authFilter;
    this.userDetailsService = userDetailsService;
  }

  @Order(1)
  @Bean
  SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {
    return http.securityMatcher(
            "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/h2-console/**", "/actuator/**")
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
        .build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.securityMatcher("/api/**")
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(
                        antMatcher("/api/v1/auth/welcome"),
                        antMatcher("/api/v1/auth/register"),
                        antMatcher("/api/v1/auth/token"))
                    .permitAll()
                    .requestMatchers(antMatcher("/api/v1/rpcdata/**"))
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .cors(withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  // Password Encoding
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
