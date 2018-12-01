package com.msekta.timesheet.DTOs.project;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectShortDTO {

    private Long id;
    private String name;
}
