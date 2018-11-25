package com.msekta.timesheet.services;

import com.msekta.timesheet.models.User;
import com.msekta.timesheet.repo.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    private UserDao userDao;

    public User getLoggedUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("I found user in security context: " + username);
        return userDao.findByUsername(username).orElseThrow(() -> new SecurityException());
    }
}
