package com.msekta.timesheet.repo;

import com.msekta.timesheet.models.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectDao extends CrudRepository<Project, Long> {
}
