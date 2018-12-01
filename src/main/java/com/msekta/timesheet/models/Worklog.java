package com.msekta.timesheet.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.msekta.timesheet.enums.WorklogStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Worklog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private Integer hourFrom;
    private Integer hourTo;
    private Integer duration;
    private String comment;
    private WorklogStatus status;
    @ManyToOne
    private Project project;
    @ManyToOne
    private User user;
    private Boolean active;
}
