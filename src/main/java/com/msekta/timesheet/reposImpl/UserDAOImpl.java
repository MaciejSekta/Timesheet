package com.msekta.timesheet.reposImpl;

import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.repos.UserDAO;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDAOImpl extends SimpleJpaRepository<User, Long> implements UserDAO {

    private final EntityManager entityManager;

    public UserDAOImpl(Class<User> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    @Override
    public Set<User> findAllByProjectsNot(Project project) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> user = query.from(User.class);
        Join<User, Project> projects = user.join("projects");

        query.select(user)
             .where(cb.notEqual(projects.get("id"), project.getId()));

        return new HashSet(entityManager.createQuery(query)
                                        .getResultList());
    }

    @Override
    public List<User> findAllByIdNotInAndRole(List<Long> users, UserRole role) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> user = query.from(User.class);

        query.select(user)
             .where(user.get("id")
                        .in(users)
                        .not())
             .where(cb.equal(user.get("role"), role));
        return entityManager.createQuery(query)
                            .getResultList();
    }

    @Override
    public List<User> findAllByIdNotInAndProjects(List<Long> users, Project project) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> user = query.from(User.class);
        Join<User, Project> projects = user.join("projects");

        query.select(user)
             .where(user.get("id")
                        .in(users)
                        .not())
             .where(cb.equal(projects, project));

        return entityManager.createQuery(query)
                            .getResultList();
    }

    @Override
    public List<User> findAllByRole(UserRole role) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);

        Root<User> user = query.from(User.class);

        query.select(user)
             .where(cb.equal(user.get("role"), role));

        return entityManager.createQuery(query)
                            .getResultList();
    }
}
