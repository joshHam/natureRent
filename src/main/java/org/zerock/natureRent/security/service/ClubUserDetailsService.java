package org.zerock.natureRent.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.repository.MemberRepository;
import org.zerock.natureRent.security.dto.MemberDTO;

import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("ClubUserDetailsService loadUserByUsername " + username);

        Optional<Member> result = memberRepository.findByEmail(username, false);
//        Optional<Member> result = memberRepository.findByEmailAndFromSocial(username, false);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check User Email or from Social ");
        }

        Member member = result.get();

        log.info("-----------------------------");
        log.info(member);

        MemberDTO clubAuthMember = new MemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                        .collect(Collectors.toSet())
        );

        log.info("Roles: " + member.getRoleSet());

        clubAuthMember.setName(member.getName());
        clubAuthMember.setFromSocial(member.isFromSocial());

        return clubAuthMember;
    }



}
