package com.msekta.timesheet.controllers;

import com.msekta.timesheet.DTOs.WorklogDTO;
import com.msekta.timesheet.services.WorklogService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/worklog")
@CrossOrigin
@Slf4j
public class WorklogController {

    @Autowired
    private WorklogService worklogService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<WorklogDTO>> getAllWorklogs() {
        try {
            return ResponseEntity.ok(worklogService.getAllWorklogs());
        } catch (Exception e) {
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest()
                                 .build();
        }
    }

    @GetMapping(value = "/timesheet")
    public ResponseEntity<List<WorklogDTO>> getAllUserWorklogs() {
        try{
            return ResponseEntity.ok(worklogService.getAllUserWorklogs());
        }catch (Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/team")
    public ResponseEntity<List<WorklogDTO>> getAllWorklogsOfMembersOfProjectsWhereUserIsManager() {
        try{
            return ResponseEntity.ok(worklogService.getAllWorklogsOfMembersOfProjectsWhereUserIsManager());
        }catch (Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> saveOrUpdateWorklog(@RequestBody WorklogDTO worklogDTO) {
        try {
            worklogService.udpateWorklog(worklogDTO);
            return ResponseEntity.ok()
                                 .build();
        } catch (Exception e) {
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest()
                                 .build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteWorklog(@PathVariable("id") Long id) {
        try {
            worklogService.deleteWorklog(id);
            return ResponseEntity.ok()
                                 .build();
        } catch (Exception e) {
        	log.info(e.getMessage(), e);
            return ResponseEntity.notFound()
                                 .build();
        }
    }

    @PutMapping(value = "/accept/{id}")
    public ResponseEntity<?> acceptWorklog(@PathVariable("id") Long id) {
        try {
            worklogService.acceptWorklog(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/accept")
    public ResponseEntity<?> acceptAllWorklogs() {
        try {
            worklogService.acceptAllWorklogs();
            return ResponseEntity.ok().build();
        }catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(value = "/reject/{id}")
    public ResponseEntity<?> rejectWorklog(@PathVariable("id") Long id) {
        try {
            worklogService.rejectWorklog(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}
