//@Configuration
//@EnableWebSecurity
//@Log4j2
//@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
//        log.info("----------------------filterChain-------------------------");
//
//        http.csrf().disable();
//        http.formLogin()
//                .loginPage("/login")  // 커스텀 로그인 페이지 경로
//                .permitAll();  // 로그인 페이지 접근은 누구나 가능하게 설정
//        http.logout();
//
//        http.oauth2Login().successHandler(clubLoginSuccessHandler());
//        http.rememberMe().tokenValiditySeconds(60*60*24*7);
//
//        // 추가 필터 설정
//        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        // 권한 설정
//        http.authorizeHttpRequests((authz) -> authz
//                .requestMatchers("/main/all").permitAll()
//                .requestMatchers("/main/member").hasAnyAuthority("USER", "OAUTH2_USER")
//                .requestMatchers("/main/admin").hasRole("ADMIN")
//                .anyRequest().authenticated()
//        );
//
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
//        http.authenticationManager(authenticationManager);
//
//        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login", jwtUtil());
//        apiLoginFilter.setAuthenticationManager(authenticationManager);
//        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public ClubLoginSuccessHandler clubLoginSuccessHandler() {
//        return new ClubLoginSuccessHandler(passwordEncoder());
//    }
//
//    @Bean
//    public ApiCheckFilter apiCheckFilter() {
//        return new ApiCheckFilter("/notes/**/*", jwtUtil());
//    }
//
//    @Bean
//    public JWTUtil jwtUtil() {
//        return new JWTUtil();
//    }
//}
