package com.msekta.timesheet.DTOs.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RegisterDTO extends LoginDTO {

    private String name;
    private String surname;
    private LocalDate bithday;
    private String userType;
    private Integer workDayHours;
}
