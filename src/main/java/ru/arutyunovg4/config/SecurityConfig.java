package ru.arutyunovg4.config; // Укажи свой пакет!

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Отключаем CSRF
                .csrf(csrf -> csrf.disable())
                // 2. Настраиваем правила доступа к URL (вместо authorizeRequests и antMatchers)
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем доступ всем без регистрации
                        .requestMatchers("/", "/login", "/registration", "/error", "/css/**", "/js/**", "/images/**").permitAll()

                        // Доступ к аккаунту для USER и ADMIN
                        .requestMatchers("/account/**").hasAnyRole("USER", "ADMIN")

                        // Доступ к админке только для ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )

                // 3. Настраиваем страницу входа (Form Login)
                .formLogin(form -> form
                        .loginPage("/login") // Указываем, что наш контроллер обрабатывает /login
                        .defaultSuccessUrl("/account", true) // Куда перенаправить после успешного входа
                        .permitAll()
                )

                // 4. Настраиваем выход из системы (Logout)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}