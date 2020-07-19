package com.msekta.timesheet.repos;

import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDAO extends CrudRepository<Project, Long> {

    List<Project> findAllByManager(User user);

    List<Project> findAllByManager_id(Long id);

    List<Project> findAllByMembers_idAndActive(Long id, Boolean active);
}
