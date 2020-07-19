package com.msekta.timesheet.reposImpl;

import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.repos.ProjectDAO;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class ProjectDAOImpl extends SimpleJpaRepository<Project, Long> implements ProjectDAO {

    public ProjectDAOImpl(Class<Project> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<Project> findAllByManager(User user) {
        return null;
    }

    @Override
    public List<Project> findAllByManager_id(Long id) {
        return null;
    }

    @Override
    public List<Project> findAllByMembers_idAndActive(Long id, Boolean active) {
        return null;
    }
}
