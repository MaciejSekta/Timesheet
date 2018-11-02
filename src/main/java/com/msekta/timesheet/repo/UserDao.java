package com.msekta.timesheet.repo;

import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    Set<User> findAllByProjectsNot(Project project);

    Set<User> findAllByProjectsNotAndRole(Project project, UserRole role);

    List<User> findAllByIdNotInAndProjects(List<Long> users, Project project);

    List<User> findAllByRole(UserRole role);
}
