package com.wadiz_goods.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "demander")
@Getter
@Setter
public class Demander {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "demander_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "demander")
    private List<Keyword> keywords = new ArrayList<>();

}
