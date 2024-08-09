package org.zerock.natureRent.entity;


import lombok.*;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
/*@Table(name = "member")  // 테이블 이름을 명시적으로 지정*/
public class Member extends BaseEntity {

    @Id
    private String email;

    private String password;

    private String name;

    private String nickname;

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>(Collections.singleton(ClubMemberRole.USER)); // 기본값 추가

    public void addMemberRole(ClubMemberRole clubMemberRole){

        roleSet.add(clubMemberRole);
    }

    @PrePersist
    public void addDefaultRole() {
        this.addMemberRole(ClubMemberRole.USER); // 기본 역할 추가
    }

}
