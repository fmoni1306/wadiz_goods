package com.wadiz_goods.domain.project;

import com.mysql.cj.log.Log;
import com.wadiz_goods.cofig.BaseTimeEntity;
import com.wadiz_goods.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.lang.Nullable;

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
public class Project extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member provider;

    private String content;

    private Long price;

    private Long purposePrice;

    private LocalDate periodStart;

    private LocalDate periodEnd;

    @Nullable
    private Boolean isStart;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Buy> buys = new ArrayList<>();

    // https://dev-elop.tistory.com/entry/JPA-orphanRemoval-%EC%9A%A9%EB%8F%84 = orphan 용도
   @OneToMany(mappedBy = "project", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public void setProvider(Member member) {
        this.provider = member;
        member.getProjects().add(this);
    }

    public void addImages(Image image) {
        this.images.add(image);

        if (image.getProject() != this) {
            image.setProject(this);
        }
    }

    // 생성 메서드
    public static Project createProject(String title, Member provider, String content, Long price, Long purposePrice, LocalDate periodStart, LocalDate periodEnd) {
        Project project = new Project();
        project.setProvider(provider);
        project.setTitle(title);
        project.setContent(content);
        project.setPrice(price);
        project.setPurposePrice(purposePrice);
        project.setPeriodStart(periodStart);
        project.setPeriodEnd(periodEnd);
        project.setIsStart(Boolean.FALSE);


        return project;

    }

}
