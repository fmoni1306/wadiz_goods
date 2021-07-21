package com.wadiz_goods.domain.member;

import com.wadiz_goods.cofig.BaseTimeEntity;
import com.wadiz_goods.domain.project.Project;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "provider")
    private List<Project> projects = new ArrayList<>();
}
