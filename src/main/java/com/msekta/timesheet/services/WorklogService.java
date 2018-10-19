package com.msekta.timesheet.services;

import com.msekta.timesheet.DTOs.WorklogDTO;
import com.msekta.timesheet.mappers.WorklogMapper;
import com.msekta.timesheet.models.Worklog;
import com.msekta.timesheet.repo.WorklogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorklogService {

    private WorklogDao worklogDao;
    private WorklogMapper worklogMapper;

    @Autowired
    public WorklogService(WorklogDao worklogDao, WorklogMapper worklogMapper) {
        this.worklogDao = worklogDao;
        this.worklogMapper = worklogMapper;
    }

    public Worklog createWorklog(WorklogDTO worklogDTO) {

        return null;
    }

    public Worklog udpateWorklog(WorklogDTO worklogDTO) {

        return null;
    }

    public void deleteWorklog(Long worklogId) {
        Worklog worklog = worklogDao.findById(worklogId)
                                    .orElseThrow(() -> new NoSuchElementException());
        worklog.setActive(false);
        worklogDao.save(worklog);
    }

    public WorklogDTO getWorklog(Long worklogId) {
        Worklog worklog = worklogDao.findById(worklogId)
                                    .orElseThrow(() -> new NoSuchElementException());
        return worklogMapper.mapModelToDTO(worklog);
    }

    public List<WorklogDTO> getAllWorklogs() {
        Set<Worklog> worklogs = (Set<Worklog>) worklogDao.findAll();
        return worklogs.stream()
                       .map(w -> worklogMapper.mapModelToDTO(w))
                       .collect(Collectors.toList());
    }
}
