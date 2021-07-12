package com.wadiz_goods.repository;

import com.wadiz_goods.domain.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final EntityManager em;

    public void save(Project project) {
        em.persist(project);
    }
}
