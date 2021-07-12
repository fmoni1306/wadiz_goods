package com.wadiz_goods.domain.project;

import com.wadiz_goods.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "project")
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member provider;

    private String content;

    private Long price;

    private Long purposePrice;

    private LocalDateTime createdDate;

    private LocalDate periodStart;

    private LocalDate periodEnd;

    // should add tag (1 : M ), add image ( 1: M)
    // private List<Tag> tag;
    // private List<Image> image




}
