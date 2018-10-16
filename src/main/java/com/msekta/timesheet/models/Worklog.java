package com.msekta.timesheet.models;

import com.msekta.timesheet.enums.WorklogStatus;
import com.msekta.timesheet.models.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@Entity
public class Worklog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private Integer hourFrom;
    private Integer hourTo;
    private String comment;
    private WorklogStatus status;
    @ManyToOne
    private Project project;
    @ManyToOne
    private User user;
}
