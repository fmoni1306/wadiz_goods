package com.wadiz_goods.service;

import com.wadiz_goods.controller.form.ProjectForm;
import com.wadiz_goods.domain.member.Member;
import com.wadiz_goods.domain.project.Project;
import com.wadiz_goods.repository.MemberRepository;
import com.wadiz_goods.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(ProjectForm form) {
        Project project = new Project();
        System.out.println("form = " + form);
        Member member = memberRepository.find(1L);
        project.setTitle(form.getTitle());
        project.setContent(form.getContent());
        project.setPrice(form.getPrice());
        project.setPurposePrice(form.getPurposePrice());
        project.setCreatedDate(LocalDateTime.now());
        project.setPeriodStart(form.getPeriodStart());
        project.setPeriodEnd(form.getPeriodEnd());

        project.setProvider(member);

        projectRepository.save(project);

        return project.getId();

    }


}
