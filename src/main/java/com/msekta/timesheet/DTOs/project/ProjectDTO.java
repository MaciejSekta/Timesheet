package com.msekta.timesheet.DTOs.project;

import com.msekta.timesheet.DTOs.WorklogDTO;
import com.msekta.timesheet.DTOs.user.UserShortDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectDTO {

    private Long id;
    private String name;
    private Boolean active;
    private List<UserShortDTO> members;
    private UserShortDTO manager;
}
