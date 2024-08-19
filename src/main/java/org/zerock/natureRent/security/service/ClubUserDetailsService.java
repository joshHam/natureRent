package org.zerock.natureRent.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.natureRent.entity.ClubMemberRole;
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

    private final PasswordEncoder passwordEncoder;

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
        clubAuthMember.setMember(member); // Member 객체 설정


        return clubAuthMember;
    }

    //회원가입
    public void register(MemberDTO memberDTO) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());

        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .password(encodedPassword)
                .name(memberDTO.getName())
                .nickname(memberDTO.getNickname())
                .fromSocial(false) // 일반 가입으로 설정
                .build();

        // 기본 역할 추가
        member.addMemberRole(ClubMemberRole.USER);

        memberRepository.save(member);
    }



}
