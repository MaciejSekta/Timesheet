package com.msekta.timesheet.DTOs;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonRootName("token")
public class JwtTokenDTO {
    private String token;
    private String type;
}
