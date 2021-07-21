package com.wadiz_goods.controller;

import com.wadiz_goods.controller.form.ProjectForm;
import com.wadiz_goods.domain.member.Member;
import com.wadiz_goods.domain.project.Project;
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
import org.springframework.web.multipart.MultipartFile;

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
    public String create(
            @RequestParam(value = "tagsList", required = false) String[] tags,
            @RequestParam(value = "image", required = false) List<MultipartFile> Images,
            @Valid ProjectForm form,
            Authentication authentication
    ) throws Exception {
        User user = (User) authentication.getPrincipal();
        System.out.println(Images.get(0));
        projectService.create(form, user, tags, Images);

        return "redirect:/";
    }

    @GetMapping("project/find")
    public String projectFind(Model model) {
        List<Project> projects = projectService.findProjects();

        model.addAttribute("projects", projects);
        return "project/projectList";
    }

    @GetMapping("project/detail")
    public String projectDetail(Model model, @RequestParam(value = "id") Long id) {
        Project project = projectService.findProject(id);

        model.addAttribute("project", project);
        return "project/projectDetail";
    }
}
