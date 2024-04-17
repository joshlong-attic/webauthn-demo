package bootiful.passkeys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.WebauthnConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;

@EnableWebSecurity
@SpringBootApplication
public class PasskeysApplication {


    public static void main(String[] args) {
        SpringApplication.run(PasskeysApplication.class, args);
    }

    @Bean
    InMemoryUserDetailsManager authentication() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("jlong")
                        .password("pw")
                        .roles("USER")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("dgarnier")
                        .password("pw")
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
        http
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login/**").permitAll()
                        .anyRequest().authenticated()
                )
                .with(new WebauthnConfigurer<>(), (passkeys) -> passkeys
                        .rpName("Spring Security Relying Party")
                        .rpId("localhost")
                        .allowedOrigins("http://localhost:8080")
                );
        return http.build();
    }

}

