package com.wadiz_goods.repository;

import com.wadiz_goods.domain.member.Member;
import com.wadiz_goods.domain.project.Buy;
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

    public void save(Buy buy) {
        em.persist(buy);
    }

    // 오늘 날짜를 받아서 한번에 변경시키기
    public void startUpdate(Project project) {
        String jpql = "update Project p set p.isStart = true where p.id =:project";
        int resultCount = em.createQuery(jpql)
                .setParameter("project", project.getId())
                .executeUpdate();
    }

    public void endUpdate(Project project) {
        String jpql = "update Project p set p.isStart = null where p.id =:project";
        int resultCount = em.createQuery(jpql)
                .setParameter("project", project.getId())
                .executeUpdate();
    }

    public List<Project> findAll() {
        String jpql = "select p From Project p";

        TypedQuery<Project> query = em.createQuery(jpql, Project.class);

        return query.getResultList();
    }

    public Project findOne(Long id) {
        return em.find(Project.class, id);
    }

    public List<Buy> findOneBuy(Project project, Member member) {
        String jpql = "select b.id From Buy b where b.project=:project and b.member=:member";
        return em.createQuery(jpql)
                .setParameter("project", project)
                .setParameter("member", member)
                .getResultList();

    }
}
