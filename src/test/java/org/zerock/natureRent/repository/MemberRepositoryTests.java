package org.zerock.natureRent.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.natureRent.entity.MemberOriginal;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberOriginalRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            MemberOriginal member = MemberOriginal.builder()
                    .email("r"+i +"@zerock.org")
                    .pw("1111")
                    .nickname("reviewer"+i).build();
            memberRepository.save(member);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMember() {

        Long mid = 1L; //Member의 mid

        MemberOriginal member = MemberOriginal.builder().mid(mid).build();

        //기존
        //memberRepository.deleteById(mid);
        //reviewRepository.deleteByMember(member);

        //순서 주의
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }










}
