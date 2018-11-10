package com.msekta.timesheet.controllers;

import com.msekta.timesheet.DTOs.StatsDTO;
import com.msekta.timesheet.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/stats")
@CrossOrigin
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping(path = "/week/hours")
    public ResponseEntity<List<StatsDTO>> getWeekHours(){
        try{
            return ResponseEntity.ok(statsService.getWeekHours());
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/week/projects")
    public ResponseEntity<List<StatsDTO>> getWeekProjects(){
        try{
            return ResponseEntity.ok(statsService.getWeekProjects());
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/week/status")
    public ResponseEntity<List<StatsDTO>> getWeekStatus(){
        try{
            return ResponseEntity.ok(statsService.getWeekWorklogsStatus());
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/month/hours")
    public ResponseEntity<List<StatsDTO>> getMonthHours(){
        try{
            return ResponseEntity.ok(statsService.getMonthHours());
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/month/projects")
    public ResponseEntity<List<StatsDTO>> getMonthProjects(){
        try{
            return ResponseEntity.ok(statsService.getMonthProjects());
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/month/status")
    public ResponseEntity<List<StatsDTO>> getMonthStatus(){
        try{
            return ResponseEntity.ok(statsService.getMonthWorklogsStatus());
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/year/hours")
    public ResponseEntity<List<StatsDTO>> getYearHours(){
        try{
            return ResponseEntity.ok(statsService.getYearHoursByMonth());
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/year/projects")
    public ResponseEntity<List<StatsDTO>> getYearProjects(){
        try{
            return ResponseEntity.ok(statsService.getYearProjects());
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/year/status")
    public ResponseEntity<List<StatsDTO>> getYearStatus(){
        try{
            return ResponseEntity.ok(statsService.getYearWorklogsStatus());
        } catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
