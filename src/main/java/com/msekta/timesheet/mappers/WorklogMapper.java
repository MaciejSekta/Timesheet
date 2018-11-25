package com.msekta.timesheet.mappers;

import com.msekta.timesheet.DTOs.WorklogDTO;
import com.msekta.timesheet.enums.WorklogStatus;
import com.msekta.timesheet.models.Worklog;
import com.msekta.timesheet.services.ProjectService;
import com.msekta.timesheet.services.UserService;
import com.msekta.timesheet.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorklogMapper {

    private UserMapper userMapper;
    private ProjectMapper projectMapper;
    private UserService userService;
    private ProjectService projectService;
    private AuthenticationService auth;

    @Autowired
    public WorklogMapper(UserMapper userMapper, ProjectMapper projectMapper, UserService userService, ProjectService projectService, AuthenticationService auth) {
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.projectService = projectService;
        this.auth = auth;
    }

    public WorklogDTO mapModelToDTO(Worklog model) {
        WorklogDTO dto = WorklogDTO.builder()
                                   .id(model.getId())
                                   .date(model.getDate())
                                   .hourFrom(model.getHourFrom())
                                   .hourTo(model.getHourTo())
                                   .duration(model.getDuration())
                                   .comment(model.getComment())
                                   .user(userMapper.mapModelToShortDTO(model.getUser()))
                                   .project(projectMapper.mapModelToShortDTO(model.getProject()))
                                   .status(model.getStatus().getName())
                                   .build();
        return dto;
    }

    public Worklog mapDTOToModel(WorklogDTO dto, Worklog model) {
        model.setDate(dto.getDate());
        model.setHourFrom(dto.getHourFrom());
        model.setHourTo(dto.getHourTo());
        model.setDuration(dto.getDuration());
        model.setComment(dto.getComment());
        model.setUser(auth.getLoggedUser());
        model.setProject(projectService.findProjectById(dto.getProject().getId()));
        model.setStatus(WorklogStatus.getEnum(dto.getStatus()));
        model.setDuration(dto.getHourTo() - dto.getHourFrom());
        model.setActive(true);
        return model;
    }
}
