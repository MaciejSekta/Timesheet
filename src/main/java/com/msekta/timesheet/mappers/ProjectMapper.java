package com.msekta.timesheet.mappers;

import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.DTOs.project.ProjectShortDTO;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    private UserMapper userMapper;
    private UserService userService;

    @Autowired
    public ProjectMapper(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    public ProjectDTO mapModelToDTO(Project model) {
        ProjectDTO dto = ProjectDTO.builder()
                                   .id(model.getId())
                                   .name(model.getName())
                                   .active(model.getActive())
                                   .manager(userMapper.mapModelToShortDTO(model.getManager()))
                                   .members(model.getMembers()
                                                  .stream()
                                                  .map(m -> userMapper.mapModelToShortDTO(m))
                                                  .collect(Collectors.toList()))
                                   .build();
        return dto;
    }

    public ProjectShortDTO mapModelToShortDTO(Project project) {
        return ProjectShortDTO.builder()
                              .id(project.getId())
                              .name(project.getName())
                              .build();
    }

    public Project mapDTOToModel(ProjectDTO dto, Project model) {
        model.setName(dto.getName());
        model.setActive(dto.getActive());
        model.setMembers(dto.getMembers()
                             .stream()
                             .map(m -> userService.findUserById(m.getId()))
                             .collect(Collectors.toList()));
        model.setManager(userService.findUserById(dto.getManager() .getId()));
        return model;
    }
}
