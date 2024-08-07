//@Override
//public void onAuthenticationSuccess(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    Authentication authentication) throws IOException, ServletException {
//
//    log.info("--------------------------------------");
//    log.info("onAuthenticationSuccess");
//
//    MemberDTO authMember = (MemberDTO) authentication.getPrincipal();
//
//    boolean fromSocial = authMember.isFromSocial();
//
//    log.info("Need Modify Member? " + fromSocial);
//
//    boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());
//
//    if (fromSocial && passwordResult) {
//        redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
//    } else {
//        // 사용자 역할(Role)에 따라 리다이렉트 경로 설정
//        String role = authMember.getAuthorities().stream()
//                .map(auth -> auth.getAuthority())
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("User has no roles"));
//
//        if (role.equals(ClubMemberRole.USER.name())) {
//            redirectStrategy.sendRedirect(request, response, "/main/member");
//        } else if (role.equals(ClubMemberRole.MANAGER.name())) {
//            redirectStrategy.sendRedirect(request, response, "/main/manager");
//        } else if (role.equals(ClubMemberRole.ADMIN.name())) {
//            redirectStrategy.sendRedirect(request, response, "/main/admin");
//        } else if (role.equals(ClubMemberRole.OAUTH2_USER.name())) {
//            redirectStrategy.sendRedirect(request, response, "/main/oauth2");
//        } else {
//            // 기본 리다이렉트 경로 설정 (필요 시 수정)
//            redirectStrategy.sendRedirect(request, response, "/");
//        }
//    }
//}
