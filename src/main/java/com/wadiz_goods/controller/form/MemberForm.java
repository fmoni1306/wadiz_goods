package com.wadiz_goods.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    private String memberName;

    private String password;
    private String rePassword;

    private Boolean purpose;



}
