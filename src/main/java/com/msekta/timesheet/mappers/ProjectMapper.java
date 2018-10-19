package com.msekta.timesheet.mappers;

import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.DTOs.project.ProjectShortDTO;
import com.msekta.timesheet.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    @Autowired
    private UserMapper userMapper;

    public ProjectDTO mapModelToDTO(Project project) {
        ProjectDTO dto = ProjectDTO.builder()
                                   .id(project.getId())
                                   .name(project.getName())
                                   .manager(userMapper.mapModelToShortDTO(project.getManager()))
                                   .memebers(project.getMemebers()
                                                    .stream()
                                                    .map(m -> userMapper.mapModelToShortDTO(m))
                                                    .collect(Collectors.toList()))
                                   .build();
        return null;
    }

    public ProjectShortDTO mapModelToShortDTO(Project project) {
        return ProjectShortDTO.builder()
                              .id(project.getId())
                              .name(project.getName())
                              .build();
    }
}
