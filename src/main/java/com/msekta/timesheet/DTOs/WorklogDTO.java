package com.msekta.timesheet.DTOs;

import com.msekta.timesheet.DTOs.project.ProjectShortDTO;
import com.msekta.timesheet.DTOs.user.UserShortDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class WorklogDTO {

    private Long id;
    private LocalDate date;
    private Integer hourFrom;
    private Integer hourTo;
    private Integer duration;
    private String comment;
    private String status;
    private ProjectShortDTO project;
    private UserShortDTO user;
}
