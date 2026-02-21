package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // 新的写法
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // 允许所有请求
                )
                .httpBasic(httpBasic -> httpBasic.disable());  // 禁用HTTP Basic认证

        return http.build();
    }
}