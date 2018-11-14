package com.msekta.timesheet.repo;

import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    Set<User> findAllByProjectsNot(Project project);

    List<User> findAllByIdNotInAndRole(List<Long> users, UserRole role);

    List<User> findAllByIdNotInAndProjects(List<Long> users, Project project);

    List<User> findAllByRole(UserRole role);

    Optional<User> findByUsername(String username);
}
