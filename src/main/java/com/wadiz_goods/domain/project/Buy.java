package com.wadiz_goods.domain.project;

import com.wadiz_goods.cofig.BaseTimeEntity;
import com.wadiz_goods.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "buy")
@Getter
@Setter
public class Buy extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buy_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Buy createBuy(Project project, Member member) {
        Buy buy = new Buy();
        buy.setProject(project);
        buy.setMember(member);


        return buy;

    }
}
