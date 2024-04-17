package bootiful.passkeys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.WebauthnConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;



@SpringBootApplication
public class PasskeysApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasskeysApplication.class, args);
    }

    @Bean
    UserDetailsService userDetailsService() {
        var builder = User.withDefaultPasswordEncoder().roles("USER").password("pw");
        return new InMemoryUserDetailsManager(
                builder.username("jlong").build(),
                builder.username("dgarniermoiroux").build()
        );
    }

    @Bean
    DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(http -> http
                        .requestMatchers("/login/**").permitAll()
                        .anyRequest().authenticated())
                .with(new WebauthnConfigurer<>(), passkeys ->
                        passkeys.allowedOrigins("http://localhost:8080")
                                .rpId("localhost")
                                .rpName("Bootiful WebAuthn")
                )
                .build();

    }

}

