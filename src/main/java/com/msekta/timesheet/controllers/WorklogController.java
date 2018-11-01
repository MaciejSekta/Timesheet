package com.msekta.timesheet.controllers;

import com.msekta.timesheet.DTOs.WorklogDTO;
import com.msekta.timesheet.services.WorklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/worklog")
@CrossOrigin
public class WorklogController {

    @Autowired
    private WorklogService worklogService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<WorklogDTO>> getAllWorklogs() {
        try {
            return ResponseEntity.ok(worklogService.getAllWorklogs());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                                 .build();
        }
    }

    @PutMapping
    public ResponseEntity<?> saveOrUpdateWorklog(@RequestBody WorklogDTO worklogDTO) {
        try {
            worklogService.udpateWorklog(worklogDTO);
            return ResponseEntity.ok()
                                 .build();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/accept")
    public ResponseEntity<?> acceptAllWorklogs() {
        try {
            worklogService.acceptAllWorklogs();
            return ResponseEntity.ok().build();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(value = "/reject/{id}")
    public ResponseEntity<?> rejectWorklog(@PathVariable("id") Long id) {
        try {
            worklogService.rejectWorklog(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
