package com.msekta.timesheet.controllers;

import com.msekta.timesheet.DTOs.user.UserShortDTO;
import com.msekta.timesheet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/short/all")
    public ResponseEntity<List<UserShortDTO>> getAllProjects(){
        try{
            return ResponseEntity.ok(userService.getAllShortUsers());
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/members/{projectId}")
    public ResponseEntity<List<UserShortDTO>> getUsersToAddToProject(@PathVariable("projectId") Long id){
        try{
            return ResponseEntity.ok(userService.getUsersToAddToProject(id));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/managers")
    public ResponseEntity<List<UserShortDTO>> getAvailableManagers(){
        try{
            return ResponseEntity.ok(userService.getAvailableManagers());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }
}
