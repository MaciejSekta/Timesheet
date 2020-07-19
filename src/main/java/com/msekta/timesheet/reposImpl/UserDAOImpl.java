package com.msekta.timesheet.reposImpl;

import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.repos.UserDAO;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

public class UserDAOImpl extends SimpleJpaRepository<User, Long> implements UserDAO {

    public UserDAOImpl(Class<User> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Set<User> findAllByProjectsNot(Project project) {
        return null;
    }

    @Override
    public List<User> findAllByIdNotInAndRole(List<Long> users, UserRole role) {
        return null;
    }

    @Override
    public List<User> findAllByIdNotInAndProjects(List<Long> users, Project project) {
        return null;
    }

    @Override
    public List<User> findAllByRole(UserRole role) {
        return null;
    }
}
