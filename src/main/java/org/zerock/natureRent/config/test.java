//@Bean
//public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
//
//    log.info("----------------------filterChain-------------------------");
//
//    http.csrf().disable();
//
//    // Form Login 설정
//    http.formLogin()
//            .loginPage("/login")
//            .successHandler(clubLoginSuccessHandler());
//
//    // OAuth2 Login 설정
//    http.oauth2Login()
//            .loginPage("/login")
//            .successHandler(clubLoginSuccessHandler());
//
//    http.rememberMe().tokenValiditySeconds(60*60*24*7);
//
//    // 필터 추가
//    http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    // AuthenticationManager 설정
//    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//    AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
//    http.authenticationManager(authenticationManager);
//
//    // APILoginFilter 설정
//    ApiLoginFilter apiLoginFilter =  new ApiLoginFilter("/api/login", jwtUtil());
//    apiLoginFilter.setAuthenticationManager(authenticationManager);
//    http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);
//
//    return http.build();
//}
