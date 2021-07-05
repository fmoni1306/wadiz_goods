package com.wadiz_goods.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String memberName;

    @NotNull
    private String password;

    private String name;

    private Boolean isProvider;

    private Boolean isDemander;

    @OneToOne(mappedBy = "member")
    private Demander demander;

    @OneToOne(mappedBy = "member")
    private Provider provider;
}
