package com.wadiz_goods.repository;

import com.wadiz_goods.domain.project.Project;
import com.wadiz_goods.domain.project.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final EntityManager em;

    public void save(Project project) {
        em.persist(project);
    }

    public void save(Tag tag) {
        em.persist(tag);
    }

    public List<Project> findAll() {
        String jpql = "select p From Project p";

        TypedQuery<Project> query = em.createQuery(jpql, Project.class);
        System.out.println(query.getResultList());

        return query.getResultList();
    }

    public Project findOne(Long id) {
        return em.find(Project.class, id);
    }
}
