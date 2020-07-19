package com.msekta.timesheet.services;

import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.DTOs.project.ProjectShortDTO;
import com.msekta.timesheet.mappers.ProjectMapper;
import com.msekta.timesheet.mappers.UserMapper;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.repos.ProjectDAO;
import com.msekta.timesheet.repos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private ProjectDAO projectDao;
    private ProjectMapper projectMapper;
    private UserMapper userMapper;
    private UserDAO userDao;

    @Autowired
    public ProjectService(ProjectDAO projectDao, ProjectMapper projectMapper, UserDAO userDao, UserMapper userMapper) {
        this.projectDao = projectDao;
        this.projectMapper = projectMapper;
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    public Project createProject(ProjectDTO projectDTO) {
        Project project = projectMapper.mapDTOToModel(projectDTO, Project.builder()
                                                                         .build());
        return projectDao.save(project);
    }

    public Project udpateProject(ProjectDTO projectDTO) {
        if (projectDTO.getId() != null) {
            Project project = projectDao.findById(projectDTO.getId())
                                        .orElseThrow(() -> new NoSuchElementException());
            return projectDao.save(projectMapper.mapDTOToModel(projectDTO, project));
        } else {
            return createProject(projectDTO);
        }
    }

    public void changeActiveProject(Long projectId) {
        Project project = projectDao.findById(projectId)
                                    .orElseThrow(() -> new NoSuchElementException());
        project.setActive(!project.getActive());
        projectDao.save(project);
    }

    public ProjectDTO getProject(Long projectId) {
        Project project = projectDao.findById(projectId)
                                    .orElseThrow(() -> new NoSuchElementException());
        return projectMapper.mapModelToDTO(project);
    }

    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = (List<Project>) projectDao.findAll();
        return projects.stream()
                       .map(p -> projectMapper.mapModelToDTO(p))
                       .collect(Collectors.toList());
    }

    public List<ProjectShortDTO> getAllShortProjects() {
        List<Project> projects = (List<Project>) projectDao.findAll();
        return projects.stream()
                       .map(p -> projectMapper.mapModelToShortDTO(p))
                       .collect(Collectors.toList());
    }

    public List<ProjectShortDTO> getAllWhereUserIsMember(Long id) {
        List<Project> projects = projectDao.findAllByMembers_idAndActive(id, true);
        return projects.stream()
                       .map(p -> projectMapper.mapModelToShortDTO(p))
                       .collect(Collectors.toList());
    }

    public Project findProjectById(Long projectId) {
        return projectDao.findById(projectId)
                         .orElseThrow(() -> new NoSuchElementException());
    }


}
