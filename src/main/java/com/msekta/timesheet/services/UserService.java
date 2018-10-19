package com.msekta.timesheet.services;

import com.msekta.timesheet.DTOs.user.RegisterDTO;
import com.msekta.timesheet.DTOs.user.UserDTO;
import com.msekta.timesheet.mappers.UserMapper;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.repo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDao userDao;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    public User createUser(RegisterDTO userDto) {

        return null;
    }

    public User udpateUser(RegisterDTO userDTO) {

        return null;
    }

    public void deleteUser(Long userId) {
        User user = userDao.findById(userId)
                           .orElseThrow(() -> new NoSuchElementException());
        user.setActive(false);
        userDao.save(user);
    }

    public UserDTO getUser(Long userId) {
        User user = userDao.findById(userId)
                           .orElseThrow(() -> new NoSuchElementException());
        return userMapper.mapModelToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        Set<User> users = (Set<User>) userDao.findAll();
        return users.stream()
                    .map(u -> userMapper.mapModelToDTO(u))
                    .collect(Collectors.toList());
    }
}
