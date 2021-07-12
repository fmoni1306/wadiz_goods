package com.wadiz_goods.controller;

import com.wadiz_goods.controller.form.ProjectForm;
import com.wadiz_goods.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String create(@Valid ProjectForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "project/create";
        }
        System.out.println("form = " + form);

        projectService.create(form);
        
        return "redirect:/";
    }
}
