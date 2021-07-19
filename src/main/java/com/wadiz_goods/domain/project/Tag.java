package com.wadiz_goods.domain.project;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "tag")
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;


    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    // 생성 메서드
    public static Tag createTag(String name, Project project) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setProject(project);
        return tag;
    }
}
