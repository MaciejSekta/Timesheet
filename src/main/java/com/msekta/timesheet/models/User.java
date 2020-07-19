package com.msekta.timesheet.models;

import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Employees")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String surname;

    private LocalDate birthday;

    private UserType userType;

    private UserRole role;

    private Integer workDayHours;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Worklog> worklogs;

    @ManyToMany
    @JoinTable(name = "projects_users", joinColumns = {
            @JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private List<Project> projects;

    @OneToOne
    private PaymentInfo paymentInfo;

    private Boolean active;

    private Integer ratePerHour;
}
