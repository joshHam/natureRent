package org.zerock.natureRent.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;

import java.util.Collection;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.zerock.natureRent.entity.Member;

import java.util.Collections;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class MemberDTO extends User  implements OAuth2User {

    private String email;

    private String password;

    private String confirmPassword;

    private String name;

    private String nickname;

    private boolean fromSocial;

    @Getter
    private Member member; // Member 엔티티
    private Map<String, Object> attr;
//    private Collection<? extends GrantedAuthority> authorities;

    // 기본 생성자
    public MemberDTO() {
        super("default", "default", Collections.emptyList());
    }




// Member 엔티티를 사용하는 기존 생성자
public MemberDTO(Member member, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
    super(member.getEmail(), member.getPassword(), authorities);
    this.member = member;
    this.email = member.getEmail();
    this.password = member.getPassword();
    this.name = member.getName();
    this.nickname = member.getNickname();
    this.fromSocial = member.isFromSocial();
    this.attr = attr != null ? attr : Collections.emptyMap(); // 속성 초기화
}
public MemberDTO(Member member, Collection<? extends GrantedAuthority> authorities) {
    this(member, authorities, null); // attr을 null로 전달
}

    // 새로운 생성자 추가 (현재 발생하는 오류 해결용)
    public MemberDTO(String username, String password, boolean fromSocial,
                     Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;
        this.attr = attr != null ? attr : Collections.emptyMap(); // 속성 초기화
    }

    public MemberDTO(String username, String password, boolean fromSocial,
                     Collection<? extends GrantedAuthority> authorities) {
        this(username, password, fromSocial, authorities, null); // attr을 null로 전달
    }


    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }


}
