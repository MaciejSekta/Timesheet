package com.msekta.timesheet.DTOs.user;

import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.DTOs.WorklogDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private LocalDate bithday;
    private String userType;
    private String role;
    private Integer workDayHours;
    private List<WorklogDTO> worklogs;
    private List<ProjectDTO> projects;
}
