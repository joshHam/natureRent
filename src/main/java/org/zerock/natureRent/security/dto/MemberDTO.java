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

    private Map<String, Object> attr;

    public MemberDTO() {
        super("defaultUsername", "defaultPassword", new ArrayList<>());
    }

    // 이 생성자는 아래 생성자를 호출하는데, 시그니처가 정확해야 함
    public MemberDTO(String username, String password, boolean fromSocial,
                     Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(username,password, fromSocial, authorities);
        this.attr = attr;
    }
//
    public MemberDTO(String username, String password, boolean fromSocial,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;

    }



//    public MemberDTO(String username, String password, String name, String nickname, boolean fromSocial,
//                     Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//        this.email = username;
//        this.password = password;
//        this.name = name;
//        this.nickname = nickname;
//        this.fromSocial = fromSocial;
//    }


    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

}
