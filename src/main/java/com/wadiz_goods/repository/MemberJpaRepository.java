package com.wadiz_goods.repository;

import com.wadiz_goods.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberName(String memberName);
}
