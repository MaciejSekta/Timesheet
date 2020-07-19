package com.msekta.timesheet.reposImpl;

import com.msekta.timesheet.enums.WorklogStatus;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.models.Worklog;
import com.msekta.timesheet.repos.WorklogDAO;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

public class WorklogDAOImpl extends SimpleJpaRepository<Worklog, Long> implements WorklogDAO {

    private final EntityManager entityManager;

    public WorklogDAOImpl(Class<Worklog> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    @Override
    public List<Worklog> findAllByActive(Boolean active) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worklog> query = cb.createQuery(Worklog.class);

        Root<Worklog> worklog = query.from(Worklog.class);

        query.select(worklog)
             .where(cb.equal(worklog.get("active"), active));

        return entityManager.createQuery(query)
                            .getResultList();
    }

    @Override
    public List<Worklog> findAllByUser(User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worklog> query = cb.createQuery(Worklog.class);

        Root<Worklog> worklog = query.from(Worklog.class);
        Join<Worklog, User> userJoin = worklog.join("user");

        query.select(worklog)
             .where(cb.equal(userJoin, user));

        return entityManager.createQuery(query)
                            .getResultList();
    }

    @Override
    public List<Worklog> findAllByUser_id(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worklog> query = cb.createQuery(Worklog.class);

        Root<Worklog> worklog = query.from(Worklog.class);
        Join<Worklog, User> user = worklog.join("user");

        query.select(worklog)
             .where(cb.equal(user.get("id"), id));

        return entityManager.createQuery(query)
                            .getResultList();
    }

    @Override
    public List<Worklog> findAllByUser_idAndDateBetween(Long id, LocalDate min, LocalDate max) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worklog> query = cb.createQuery(Worklog.class);

        Root<Worklog> worklog = query.from(Worklog.class);
        Join<Worklog, User> user = worklog.join("user");

        query.select(worklog)
             .where(cb.equal(user.get("id"), id))
             .where(cb.between(worklog.get("date"), min, max));

        return entityManager.createQuery(query)
                            .getResultList();
    }

    @Override
    public List<Worklog> findAllByProjectIn(List<Project> projects) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worklog> query = cb.createQuery(Worklog.class);

        Root<Worklog> worklog = query.from(Worklog.class);
        Join<Worklog, Project> project = worklog.join("project");

        query.select(worklog)
             .where(project.in(projects));

        return entityManager.createQuery(query)
                            .getResultList();
    }

    @Override
    public List<Worklog> findAllByUserAndDate(User user, LocalDate date) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worklog> query = cb.createQuery(Worklog.class);

        Root<Worklog> worklog = query.from(Worklog.class);
        Join<Worklog, User> userJoin = worklog.join("user");

        query.select(worklog)
             .where(cb.equal(userJoin, user))
             .where(cb.equal(worklog.get("date"), date));

        return entityManager.createQuery(query)
                            .getResultList();
    }

    @Override
    public Long countAllByStatusAndUserAndDateBetween(WorklogStatus status, User user, LocalDate min, LocalDate max) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<Worklog> worklog = query.from(Worklog.class);
        Join<Worklog, User> userJoin = worklog.join("user");

        query.select(cb.count(worklog))
             .where(cb.equal(userJoin, userJoin))
             .where(cb.between(worklog.get("date"), min, max));

        return entityManager.createQuery(query)
                            .getSingleResult();
    }
}
