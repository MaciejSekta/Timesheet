package com.msekta.timesheet.mappers;

import com.msekta.timesheet.DTOs.WorklogDTO;
import com.msekta.timesheet.models.Worklog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorklogMapper {

    private UserMapper userMapper;
    private ProjectMapper projectMapper;

    @Autowired
    public WorklogMapper(UserMapper userMapper, ProjectMapper projectMapper) {
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
    }

    public WorklogDTO mapModelToDTO(Worklog worklog) {
        WorklogDTO dto = WorklogDTO.builder()
                                   .id(worklog.getId())
                                   .date(worklog.getDate())
                                   .hourFrom(worklog.getHourFrom())
                                   .hourTo(worklog.getHourTo())
                                   .duration(worklog.getDuration())
                                   .comment(worklog.getComment())
                                   .user(userMapper.mapModelToShortDTO(worklog.getUser()))
                                   .project(projectMapper.mapModelToShortDTO(worklog.getProject()))
                                   .build();
        return dto;
    }
}
