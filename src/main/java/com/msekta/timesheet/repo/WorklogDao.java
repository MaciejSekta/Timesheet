package com.msekta.timesheet.repo;

import com.msekta.timesheet.enums.WorklogStatus;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.models.Worklog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorklogDao extends CrudRepository<Worklog, Long> {

    List<Worklog> findAllByActive(Boolean active);

    List<Worklog> findAllByUser(User user);

    List<Worklog> findAllByUser_id(Long id);

    List<Worklog> findAllByUser_idAndDateBetween(Long id, LocalDate min, LocalDate max);

    List<Worklog> findAllByProjectIn(List<Project> projects);

    List<Worklog> findAllByUserAndDate(User user, LocalDate date);

    Long countAllByStatusAndUserAndDateBetween(WorklogStatus status, User user, LocalDate min, LocalDate max);
}
