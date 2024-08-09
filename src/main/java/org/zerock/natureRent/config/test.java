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
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        log.info("----------------------filterChain-------------------------");
//
//        http.csrf().disable();  // CSRF 비활성화
//        http.formLogin().loginPage("/login").successHandler(clubLoginSuccessHandler());  // 폼 로그인 설정
//        http.oauth2Login().loginPage("/login").successHandler(clubLoginSuccessHandler());  // OAuth2 로그인 설정
//        http.rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7);  // Remember me 설정
//        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);  // API 체크 필터 추가
//        http.addFilterBefore(apiLoginFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))), UsernamePasswordAuthenticationFilter.class);  // API 로그인 필터 추가
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
//    public ApiLoginFilter apiLoginFilter(AuthenticationManager authenticationManager) {
//        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login", jwtUtil());
//        apiLoginFilter.setAuthenticationManager(authenticationManager);
//        return apiLoginFilter;
//    }
//
//    @Bean
//    public JWTUtil jwtUtil() {
//        return new JWTUtil();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//}
