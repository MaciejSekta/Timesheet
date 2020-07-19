package com.msekta.timesheet.reposImpl;

import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.repos.ProjectDAO;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProjectDAOImpl extends SimpleJpaRepository<Project, Long> implements ProjectDAO {

    private final EntityManager entityManager;

    public ProjectDAOImpl(Class<Project> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    @Override
    public List<Project> findAllByManager(User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = cb.createQuery(Project.class);

        Root<Project> project = query.from(Project.class);
        Join<Project, User> manager = project.join("manager");

        query.select(project)
             .where(cb.equal(manager, user));

        return entityManager.createQuery(query)
                            .getResultList();
    }

    @Override
    public List<Project> findAllByManagerId(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = cb.createQuery(Project.class);

        Root<Project> project = query.from(Project.class);
        Join<Project, User> userJoin = project.join("manager");

        query.select(project)
             .where(cb.equal(userJoin.get("id"), id));

        return entityManager.createQuery(query)
                            .getResultList();
    }

    @Override
    public List<Project> findAllByMembersIdAndActive(Long id, Boolean active) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = cb.createQuery(Project.class);

        Root<Project> project = query.from(Project.class);
        Join<Project, User> userJoin = project.join("manager");

        query.select(project)
             .where(cb.equal(userJoin.get("id"), id))
             .where(cb.equal(project.get("active"), active));

        return entityManager.createQuery(query)
                            .getResultList();
    }
}
