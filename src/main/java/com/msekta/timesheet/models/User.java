package com.msekta.timesheet.models;

import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.enums.UserType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private LocalDate bithday;
    private UserType userType;
    private UserRole role;
    private Integer workDayHours;
    @OneToMany
    private List<Worklog> worklogs;
    @ManyToMany
    @JoinTable(name = "projects_users", joinColumns = {
            @JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<Project> projects;




}
