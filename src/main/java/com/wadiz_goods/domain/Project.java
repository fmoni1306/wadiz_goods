package com.wadiz_goods.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Project {

    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long id;
}
