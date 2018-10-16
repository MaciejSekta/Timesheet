package com.msekta.timesheet.DTOs.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserShortDTO {

    private Long id;
    private String name;
    private String surname;
}
