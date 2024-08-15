package org.zerock.natureRent.security;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.entity.ClubMemberRole;
import org.zerock.natureRent.repository.MemberRepository;
import org.zerock.natureRent.security.service.ClubUserDetailsService;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClubUserDetailsService clubUserDetailsService;

    @Test
    public void insertDummies() {

        //1 - 80까지는 USER만 지정
        //81- 90까지는 USER,MANAGER
        //91- 100까지는 USER,MANAGER,ADMIN

        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("user"+i+"@zerock.org")
                    .name("사용자"+i)
                    .fromSocial(false)
                    .roleSet(new HashSet<ClubMemberRole>())
                    .password(  passwordEncoder.encode("1111") )
                    .build();

            //default role
            member.addMemberRole(ClubMemberRole.USER);

            if(i > 90){
                member.addMemberRole(ClubMemberRole.MANAGER);
            }

            if(i > 95){
                member.addMemberRole(ClubMemberRole.ADMIN);
            }

            repository.save(member);

        });

    }

    @Test
    public void testRead() {

        Optional<Member> result = repository.findByEmail("user222@zerock.org", false);

        Member member = result.get();

        System.out.println(member);

    }

    @Test
    public void testLoadUserByUsername() {
        UserDetails userDetails = clubUserDetailsService.loadUserByUsername("user222@zerock.org");

        assertNotNull(userDetails);
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGER")));
    }


}
