package com.wadiz_goods.domain.project;

import com.wadiz_goods.cofig.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Table(name = "image")
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    private String originImageName;

    @Column(nullable = false)
    private String imagePath;

    private Long imageSize;

    @Builder
    public Image(String originImageName, String imagePath, Long imageSize) {
        this.originImageName = originImageName;
        this.imagePath = imagePath;
        this.imageSize = imageSize;
    }

    public void setProject(Project project) {
        this.project = project;

        if (!project.getImages().contains(this)) {
            project.getImages().add(this);
        }

    }
}
