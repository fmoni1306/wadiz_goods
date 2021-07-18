package com.wadiz_goods.controller.form;

import com.wadiz_goods.domain.project.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class ProjectForm {

    private String title;

    private String content;

    private Long price;

    private Long purposePrice;

//    private List<Tag> tagsList;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate periodStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate periodEnd;


}
