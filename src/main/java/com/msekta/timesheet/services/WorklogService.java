package com.msekta.timesheet.services;

import com.msekta.timesheet.DTOs.WorklogDTO;
import com.msekta.timesheet.enums.WorklogStatus;
import com.msekta.timesheet.mappers.WorklogMapper;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.Worklog;
import com.msekta.timesheet.repo.ProjectDao;
import com.msekta.timesheet.repo.WorklogDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorklogService {

    private WorklogDao worklogDao;
    private WorklogMapper worklogMapper;
    private ProjectDao projectDao;

    @Autowired
    public WorklogService(WorklogDao worklogDao, WorklogMapper worklogMapper, ProjectDao projectDao) {
        this.worklogDao = worklogDao;
        this.worklogMapper = worklogMapper;
        this.projectDao = projectDao;
    }

    public Worklog createWorklog(WorklogDTO worklogDTO) {
        Worklog worklog = worklogMapper.mapDTOToModel(worklogDTO, Worklog.builder().build());
        return worklogDao.save(worklog);
    }

    public Worklog udpateWorklog(WorklogDTO worklogDTO) {
        if(worklogDTO.getId() != null) {
            Worklog worklog = worklogDao.findById(worklogDTO.getId())
                                        .orElseThrow(() -> new NoSuchElementException());
            worklogMapper.mapDTOToModel(worklogDTO, worklog);
            return worklogDao.save(worklog);
        }else{
            return createWorklog(worklogDTO);
        }
    }

    public void deleteWorklog(Long id) {
        Worklog worklog = worklogDao.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException());
        worklog.setActive(false);
        worklogDao.save(worklog);
    }

    public WorklogDTO getWorklog(Long id) {
        Worklog worklog = worklogDao.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException());
        return worklogMapper.mapModelToDTO(worklog);
    }

    public List<WorklogDTO> getAllWorklogs() {
        List<Worklog> worklogs = worklogDao.findAllByActive(true);
        return worklogs.stream()
                       .map(w -> worklogMapper.mapModelToDTO(w))
                       .collect(Collectors.toList());
    }

    public List<WorklogDTO> getAllUserWorklogs(){
        Long id = 1L; // to remove when sec
        List<Worklog> worklogs = worklogDao.findAllByUser_id(id);
        return worklogs.stream()
                       .map(w -> worklogMapper.mapModelToDTO(w))
                       .collect(Collectors.toList());
    }

    public List<WorklogDTO> getAllWorklogsOfMembersOfProjectsWhereUserIsManager(){
        Long id = 2L; // to remove when sec
        List<Project> projectsWhereUserIsAdmin = projectDao.findAllByManager_id(id);
        List<Worklog> worklogs = worklogDao.findAllByProjectIn(projectsWhereUserIsAdmin);
        return worklogs.stream()
                       .map(w -> worklogMapper.mapModelToDTO(w))
                       .collect(Collectors.toList());
    }

    public void acceptWorklog(Long id){
        Worklog worklog = worklogDao.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException());
        worklog.setStatus(WorklogStatus.ACCEPTED);
        worklogDao.save(worklog);
    }

    public void rejectWorklog(Long id){
        Worklog worklog = worklogDao.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException());
        worklog.setStatus(WorklogStatus.REJECTED);
        worklogDao.save(worklog);
    }

    // Accept only worklogs of users which are members of projects of logged manager. Accept only with padding status.
    public void acceptAllWorklogs(){
        worklogDao.findAll().forEach(w -> {
            w.setStatus(WorklogStatus.ACCEPTED);
            worklogDao.save(w);
        });
    }
}
