package com.msekta.timesheet.services;

import com.msekta.timesheet.DTOs.user.RegisterDTO;
import com.msekta.timesheet.DTOs.user.UserDTO;
import com.msekta.timesheet.DTOs.user.UserShortDTO;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.repo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User createUser(RegisterDTO userDto){

        return null;
    }

    public User udpateUser(RegisterDTO userDTO){

        return null;
    }

    public void deleteUser(Long userId){

    }

    public UserDTO getUser(Long userId){

        return null;
    }

    public List<UserShortDTO> getAllUsers(){

        return null;
    }
}
