package com.msekta.timesheet.controllers;

import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.DTOs.project.ProjectShortDTO;
import com.msekta.timesheet.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(value = "/short/all")
    public ResponseEntity<List<ProjectShortDTO>> getAllShortProjects(){
        try{
           return ResponseEntity.ok(projectService.getAllShortProjects());
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ProjectDTO>> getAllProjects(){
        try{
            return ResponseEntity.ok(projectService.getAllProjects());
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }
}
