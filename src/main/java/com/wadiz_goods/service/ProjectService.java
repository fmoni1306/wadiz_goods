package com.wadiz_goods.service;

import com.wadiz_goods.controller.form.ProjectForm;
import com.wadiz_goods.domain.member.Member;
import com.wadiz_goods.domain.project.Project;
import com.wadiz_goods.domain.project.Tag;
import com.wadiz_goods.repository.MemberJpaRepository;
import com.wadiz_goods.repository.MemberRepository;
import com.wadiz_goods.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(ProjectForm form, User user, String[] tags) {
        Member member = memberRepository.findByMemberName(user.getUsername());
        List<Tag> tagList = new ArrayList<Tag>();
        for (String a : tags) {
            Tag tag = Tag.createTag(a);
            tagList.add(tag);
        }
        Project project = Project.createProject(
                form.getTitle(),
                member,
                form.getContent(),
                form.getPrice(),
                form.getPurposePrice(),
                form.getPeriodStart(),
                form.getPeriodEnd(),
                tagList
        );

        project.setProvider(member);

        projectRepository.save(project);

        return project.getId();

    }


}
