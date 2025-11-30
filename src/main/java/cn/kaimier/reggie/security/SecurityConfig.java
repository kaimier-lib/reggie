package cn.kaimier.reggie.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${security.dev:false}")
    private boolean isDev;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    if (isDev) {
                        auth.anyRequest().permitAll();
                    } else {
                        auth.requestMatchers(
                                        "/employee/login",
                                        "/admin/page/login/login.html",
                                        "/admin/api/**",
                                        "/admin/js/**",
                                        "/admin/images/**",
                                        "/admin/plugins/**",
                                        "/admin/styles/**",
                                        "/admin/favicon.ico",
                                        "/employee/login",
                                        "/employee/logout",
                                        "/admin/**",
                                        "/user/**",
                                        "/common/**",
                                        "/user/sendMsg",
                                        "/user/login"
                                ).permitAll()

                                .anyRequest().authenticated();
                    }
                })
                .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    )
                            .formLogin(form -> form.disable())
                            .exceptionHandling(ex ->
                                    ex.authenticationEntryPoint((request, response, authException) -> {
                                        response.sendRedirect("/admin/page/login/login.html");
                                    })
                            )
                            .headers(headers -> headers
                                    .frameOptions(frame -> frame.sameOrigin()));
                    return http.build();
                }

    }
