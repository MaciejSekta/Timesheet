package com.msekta.timesheet.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsDTO {

    private String name;
    private int value;
}
