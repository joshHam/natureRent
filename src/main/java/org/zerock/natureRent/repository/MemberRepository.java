package org.zerock.natureRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.natureRent.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
