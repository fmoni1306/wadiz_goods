package com.wadiz_goods.repository;

import com.wadiz_goods.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository{

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public Member findByMemberName(String memberName) {
        return em.createQuery("select m from Member m where m.memberName = :memberName", Member.class)
                .setParameter("memberName", memberName)
                .getSingleResult();

    }




}
