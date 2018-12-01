package com.msekta.timesheet.DTOs.project;

import java.util.List;

import com.msekta.timesheet.DTOs.user.UserShortDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDTO {

    private Long id;
    private String name;
    private Boolean active;
    private List<UserShortDTO> members;
    private UserShortDTO manager;
}
