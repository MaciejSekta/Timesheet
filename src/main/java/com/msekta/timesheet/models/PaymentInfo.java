package com.msekta.timesheet.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String nip;
    private String street;
    private String houseNo;
    private String city;
    private String postalCode;
    private String bankAccountNo;
    @OneToOne
    private User user;
}
