package com.msekta.timesheet.DTOs.user;

import com.msekta.timesheet.DTOs.PaymentInfoDTO;
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
    private Integer ratePerHour;
    private PaymentInfoDTO paymentInfo;
    private String email;
}
