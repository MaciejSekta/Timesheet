package com.msekta.timesheet.DTOs.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(builderMethodName = "userBuilder")
public class UserDTO extends LoginDTO {

    private Long id;
    private String name;
    private String surname;
    private LocalDate birthday;
    private String userType;
    private Integer workDayHours;
    private Boolean active;
    private String role;
}
