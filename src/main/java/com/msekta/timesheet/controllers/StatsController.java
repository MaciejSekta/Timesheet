package com.msekta.timesheet.controllers;

import com.msekta.timesheet.DTOs.StatsDTO;
import com.msekta.timesheet.services.StatsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/stats")
@CrossOrigin
@Slf4j
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping(path = "/{id}/week/hours")
    public ResponseEntity<List<StatsDTO>> getWeekHours(@PathVariable("id") Long userId){
        try{
            return ResponseEntity.ok(statsService.getWeekHours(userId));
        } catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}/week/projects")
    public ResponseEntity<List<StatsDTO>> getWeekProjects(@PathVariable("id") Long userId){
        try{
            return ResponseEntity.ok(statsService.getWeekProjects(userId));
        } catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}/week/status")
    public ResponseEntity<List<StatsDTO>> getWeekStatus(@PathVariable("id") Long userId){
        try{
            return ResponseEntity.ok(statsService.getWeekWorklogsStatus(userId));
        } catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}/month/hours")
    public ResponseEntity<List<StatsDTO>> getMonthHours(@PathVariable("id") Long userId){
        try{
            return ResponseEntity.ok(statsService.getMonthHours(userId));
        } catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}/month/projects")
    public ResponseEntity<List<StatsDTO>> getMonthProjects(@PathVariable("id") Long userId){
        try{
            return ResponseEntity.ok(statsService.getMonthProjects(userId));
        } catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}/month/status")
    public ResponseEntity<List<StatsDTO>> getMonthStatus(@PathVariable("id") Long userId){
        try{
            return ResponseEntity.ok(statsService.getMonthWorklogsStatus(userId));
        } catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}/year/hours")
    public ResponseEntity<List<StatsDTO>> getYearHours(@PathVariable("id") Long userId){
        try{
            return ResponseEntity.ok(statsService.getYearHoursByMonth(userId));
        } catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}/year/projects")
    public ResponseEntity<List<StatsDTO>> getYearProjects(@PathVariable("id") Long userId){
        try{
            return ResponseEntity.ok(statsService.getYearProjects(userId));
        } catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{id}/year/status")
    public ResponseEntity<List<StatsDTO>> getYearStatus(@PathVariable("id") Long userId){
        try{
            return ResponseEntity.ok(statsService.getYearWorklogsStatus(userId));
        } catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}
