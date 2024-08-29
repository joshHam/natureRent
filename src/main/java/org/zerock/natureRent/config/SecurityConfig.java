package org.zerock.natureRent.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.natureRent.security.filter.ApiCheckFilter;
import org.zerock.natureRent.security.filter.ApiLoginFilter;
import org.zerock.natureRent.security.handler.ClubLoginSuccessHandler;
import org.zerock.natureRent.security.service.ClubUserDetailsService;
import org.zerock.natureRent.security.util.JWTUtil;

@Configuration
@EnableWebSecurity
@Log4j2
@EnableMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

//    private final ClubUserDetailsService clubUserDetailsService;
//
//    public SecurityConfig(ClubUserDetailsService clubUserDetailsService) {
//        this.clubUserDetailsService = clubUserDetailsService;
//    }
//private final JWTUtil jwtUtil;
//
//    public SecurityConfig(JWTUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http, ClubUserDetailsService clubUserDetailsService) throws Exception {

        log.info("----------------------filterChain-------------------------");


//        http.authorizeHttpRequests()
//                .requestMatchers("/main/all").permitAll()
//                .requestMatchers("/main/member").hasAnyAuthority("USER","OAUTH2_USER")
//                .requestMatchers("/main/admin").hasRole("ADMIN");


//        http.formLogin();
        http.csrf().disable();
//        http.csrf();


        // 권한 설정
        http.authorizeHttpRequests(authz -> authz

                .requestMatchers("/api/check-email","/main/all", "/login", "/register",
                        "/assets/**","/uploadAjax", "/display/**",
                        "/product/product-grids", "/blog/blog-grid-sidebar"
                        , "/css/**", "/js/**", "/images/**","/webjars/**").permitAll()
                .requestMatchers("/main/member")
                .hasAnyAuthority("USER", "OAUTH2_USER")
                .requestMatchers("/main/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
        );






        // Form Login 설정
//        http.formLogin()
//                .loginPage("/login")
//                .successHandler(clubLoginSuccessHandler());
        // Form Login 설정
        http.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .successHandler(clubLoginSuccessHandler())
        );


        // OAuth2 Login 설정
//        http.oauth2Login()
//                .loginPage("/login")
//                .successHandler(clubLoginSuccessHandler());
        // OAuth2 Login 설정
        http.oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/login")
                .successHandler(clubLoginSuccessHandler())
        );

        // RememberMe 설정
//        http.rememberMe().tokenValiditySeconds(60*60*24*7);
//        http.rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7);
// RememberMe 설정 (새로운 방식)
        http.rememberMe(rememberMe -> rememberMe
                .key("uniqueAndSecret") // RememberMe 기능을 위한 key 설정
                .tokenValiditySeconds(60 * 60 * 24 * 7) // 7일간 유효한 토큰
                .userDetailsService(clubUserDetailsService) // UserDetailsService 설정
        );


        //// Custom Filter 설정
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);

        // 권한 설정
//        http.authorizeHttpRequests((authz) -> authz
//                .requestMatchers("/main/all").permitAll()
//                .requestMatchers("/main/member").hasAnyAuthority("USER", "OAUTH2_USER")
//                .requestMatchers("/main/admin").hasRole("ADMIN")
//                .anyRequest().authenticated()
//        );

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        //authenticationManagerBuilder.userDetailsService(apiUserDetailsService).passwordEncoder(passwordEncoder());
        // Get AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        //반드시 필요 AuthenticationManager 설정
        http.authenticationManager(authenticationManager);

        //APILoginFilter 설정
        ApiLoginFilter apiLoginFilter =  new ApiLoginFilter("/api/login", jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user1")
//                .password("$2a$10$54b4w2aKbDkcEr5BLISfiubKcmZo7kVp5B0FqyUZ9SdMp9TXqNgxe")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public ClubLoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public ApiCheckFilter apiCheckFilter() {

        return new ApiCheckFilter("/notes/**/*", jwtUtil());
    }

    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }




}