package com.wadiz_goods.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class ProjectForm {

    private String title;

    private String content;

    private Long price;

    private Long purposePrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate periodStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate periodEnd;


}
