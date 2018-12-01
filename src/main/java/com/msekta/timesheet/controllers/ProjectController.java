package com.msekta.timesheet.controllers;

import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.DTOs.project.ProjectShortDTO;
import com.msekta.timesheet.services.ProjectService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/project")
@CrossOrigin
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(value = "/short/all")
    public ResponseEntity<List<ProjectShortDTO>> getAllShortProjects(){
        try{
           return ResponseEntity.ok(projectService.getAllShortProjects());
        }catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<ProjectShortDTO>> getAllWhereUserIsMember(){
        try{
            return ResponseEntity.ok(projectService.getAllWhereUserIsMember());
        }catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ProjectDTO>> getAllProjects(){
        try{
            return ResponseEntity.ok(projectService.getAllProjects());
        }catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping
    public ResponseEntity<?> createOrUpdateProject(@RequestBody ProjectDTO project){
        try{
            projectService.udpateProject(project);
            return ResponseEntity.ok().build();
        }catch (Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> changeActiveProject(@PathVariable("id") Long id){
        try{
            projectService.changeActiveProject(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}
