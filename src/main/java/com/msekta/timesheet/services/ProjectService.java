package com.msekta.timesheet.services;

import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.DTOs.project.ProjectShortDTO;
import com.msekta.timesheet.DTOs.user.UserShortDTO;
import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.mappers.ProjectMapper;
import com.msekta.timesheet.mappers.UserMapper;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.repo.ProjectDao;
import com.msekta.timesheet.repo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private ProjectDao projectDao;
    private ProjectMapper projectMapper;
    private UserMapper userMapper;
    private UserDao userDao;

    @Autowired
    public ProjectService(ProjectDao projectDao, ProjectMapper projectMapper, UserDao userDao, UserMapper userMapper) {
        this.projectDao = projectDao;
        this.projectMapper = projectMapper;
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    public Project createProject(ProjectDTO projectDTO) {
        Project project = projectMapper.mapDTOToModel(projectDTO, Project.builder().build());
        return projectDao.save(project);
    }

    public Project udpateProject(ProjectDTO projectDTO) {
        if(projectDTO.getId() != null) {
            Project project = projectDao.findById(projectDTO.getId())
                                        .orElseThrow(() -> new NoSuchElementException());
            return projectDao.save(projectMapper.mapDTOToModel(projectDTO, project));
        }else{
            return createProject(projectDTO);
        }
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

    public Project findProjectById(Long projectId){
        return projectDao.findById(projectId)
                         .orElseThrow(() -> new NoSuchElementException());
    }

    public List<UserShortDTO> getUsersToAddToProject(Long id){
        Project project = projectDao.findById(id).orElseThrow(() -> new NoSuchElementException());
        return userDao.findAllByProjectsNotAndRole(project, UserRole.WORKER).stream()
               .map(u -> userMapper.mapModelToShortDTO(u))
               .collect(Collectors.toList());

    }

    public List<UserShortDTO> getAvailableManagers(){
        return userDao.findAllByRole(UserRole.MANAGER).stream()
                      .map(u -> userMapper.mapModelToShortDTO(u))
                      .collect(Collectors.toList());
    }
}
