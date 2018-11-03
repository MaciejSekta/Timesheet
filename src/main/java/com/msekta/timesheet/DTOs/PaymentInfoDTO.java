package com.msekta.timesheet.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentInfoDTO {

    private Long id;
    private String name;
    private String nip;
    private String street;
    private String houseNo;
    private String city;
    private String postalCode;
    private String bankAccountNo;
}
