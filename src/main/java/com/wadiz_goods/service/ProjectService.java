package com.wadiz_goods.service;

import com.wadiz_goods.controller.form.ProjectForm;
import com.wadiz_goods.domain.member.Member;
import com.wadiz_goods.domain.project.Project;
import com.wadiz_goods.domain.project.Tag;
import com.wadiz_goods.repository.MemberRepository;
import com.wadiz_goods.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(ProjectForm form, User user, String[] tags) {
        Member member = memberRepository.findByMemberName(user.getUsername());
        List<Tag> tagList = new ArrayList<>();


        Project project = Project.createProject(
                form.getTitle(),
                member,
                form.getContent(),
                form.getPrice(),
                form.getPurposePrice(),
                form.getPeriodStart(),
                form.getPeriodEnd()
        );

        project.setProvider(member);
        for (String a : tags) {
            Tag tag = Tag.createTag(a, project);
            tagList.add(tag);
            projectRepository.save(tag);
        }
        projectRepository.save(project);


        return project.getId();

    }

    // 프로젝트 전체 조회
    public List<Project> findProjects() {
        return projectRepository.findAll();
    }
}
