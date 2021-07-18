package com.wadiz_goods.controller;

import com.wadiz_goods.controller.form.ProjectForm;
import com.wadiz_goods.domain.member.Member;
import com.wadiz_goods.domain.project.Tag;
import com.wadiz_goods.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("project/create")
    public String create(Model model) {
        model.addAttribute("form", new ProjectForm());
        return "project/create";
    }

    @PostMapping("project/create")
    public String create(@RequestParam(value = "tagsList", required=true) String[] tags, @Valid ProjectForm form, Authentication authentication) {

//        if (result.hasErrors()) {
//            return "project/create";
//        }

        User user = (User) authentication.getPrincipal();

        projectService.create(form,user, tags);

        return "redirect:/";
    }
}
