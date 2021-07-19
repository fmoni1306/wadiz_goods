package com.wadiz_goods.domain.project;

import com.mysql.cj.log.Log;
import com.wadiz_goods.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();
    // private List<Image> image

    public void setProvider(Member member) {
        this.provider = member;
        member.getProjects().add(this);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.setProject(this);
    }

    // 생성 메서드
    public static Project createProject(String title, Member provider, String content, Long price, Long purposePrice, LocalDate periodStart, LocalDate periodEnd) {
        Project project = new Project();
        project.setProvider(provider);
        project.setTitle(title);
        project.setContent(content);
        project.setPrice(price);
        project.setPurposePrice(purposePrice);
        project.setCreatedDate(LocalDateTime.now());
        project.setPeriodStart(periodStart);
        project.setPeriodEnd(periodEnd);



        return project;

    }
}
