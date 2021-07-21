package com.wadiz_goods.service;

import com.wadiz_goods.controller.form.MemberForm;
import com.wadiz_goods.domain.member.Member;
import com.wadiz_goods.repository.MemberJpaRepository;
import com.wadiz_goods.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public Long join(MemberForm form) {
        Member member = new Member();
        member.setMemberName(form.getMemberName());
        member.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
        if (form.getPurpose()) {
            member.setIsProvider(true);
        } else {
            member.setIsDemander(true);
        }

        memberRepository.save(member);

        return member.getId();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> userWrapper = memberJpaRepository.findByMemberName(username);
        Member member = userWrapper.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        return new org.springframework.security.core.userdetails.User(member.getMemberName(),
                member.getPassword(),
                authorities);
    }
}
