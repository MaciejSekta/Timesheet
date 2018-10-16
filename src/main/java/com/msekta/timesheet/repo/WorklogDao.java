package com.msekta.timesheet.repo;

import com.msekta.timesheet.models.Worklog;
import org.springframework.data.repository.CrudRepository;

public interface WorklogDao extends CrudRepository<Worklog, Long> {
}
