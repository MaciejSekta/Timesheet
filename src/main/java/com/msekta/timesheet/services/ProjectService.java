package com.msekta.timesheet.services;

import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.mappers.ProjectMapper;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.repo.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectService {

    private ProjectDao projectDao;
    private ProjectMapper projectMapper;

    @Autowired
    public ProjectService(ProjectDao projectDao, ProjectMapper projectMapper) {
        this.projectDao = projectDao;
        this.projectMapper = projectMapper;
    }

    public Project createProject(ProjectDTO projectDTO) {

        return null;
    }

    public Project udpateProject(ProjectDTO projectDTO) {

        return null;
    }

    public void deleteProject(Long projectId) {
        Project project = projectDao.findById(projectId)
                                    .orElseThrow(() -> new NoSuchElementException());
        project.setActive(false);
        projectDao.save(project);
    }

    public ProjectDTO getProject(Long projectId) {
        Project project = projectDao.findById(projectId)
                                    .orElseThrow(() -> new NoSuchElementException());
        return projectMapper.mapModelToDTO(project);
    }

    public List<ProjectDTO> getAllProjects() {
        Set<Project> projects = (Set<Project>) projectDao.findAll();
        return projects.stream()
                       .map(p -> projectMapper.mapModelToDTO(p))
                       .collect(Collectors.toList());
    }
}
