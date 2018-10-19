package com.msekta.timesheet.DTOs.project;

import com.msekta.timesheet.DTOs.user.UserShortDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectShortDTO {

    private Long id;
    private String name;
}
