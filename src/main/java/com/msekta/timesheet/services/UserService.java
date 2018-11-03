package com.msekta.timesheet.services;

import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.DTOs.user.UserDTO;
import com.msekta.timesheet.DTOs.user.UserDetailsDTO;
import com.msekta.timesheet.DTOs.user.UserShortDTO;
import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.mappers.UserMapper;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.repo.ProjectDao;
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
    private ProjectDao projectDao;

    @Autowired
    public UserService(UserDao userDao, UserMapper userMapper, ProjectDao projectDao) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.projectDao = projectDao;
    }

    public User createUser(UserDTO userDto) {
        User user = userMapper.mapDTOToModel(userDto, User.builder()
                                                          .build());
        return userDao.save(user);
    }

    public User udpateUser(UserDTO userDto) {
        if (userDto.getId() != null) {
            User user = userDao.findById(userDto.getId())
                               .orElseThrow(() -> new NoSuchElementException());
            userMapper.mapDTOToModel(userDto, user);
            return userDao.save(user);
        } else {
            return createUser(userDto);
        }

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
        List<User> users = (List<User>) userDao.findAll();
        return users.stream()
                    .map(u -> userMapper.mapModelToDTO(u))
                    .collect(Collectors.toList());
    }

    public List<UserShortDTO> getUsersToAddToProject(Long id) {
        Project project = projectDao.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException());
        List<Long> usersIds = project.getMembers()
                                     .stream()
                                     .map(u -> u.getId())
                                     .collect(Collectors.toList());
        return userDao.findAllByIdNotInAndRole(usersIds, UserRole.WORKER)
                      .stream()
                      .map(u -> userMapper.mapModelToShortDTO(u))
                      .collect(Collectors.toList());

    }

    public List<UserShortDTO> getAvailableManagers() {
        return userDao.findAllByRole(UserRole.MANAGER)
                      .stream()
                      .map(u -> userMapper.mapModelToShortDTO(u))
                      .collect(Collectors.toList());
    }

    public List<UserShortDTO> getAllShortUsers() {
        List<User> users = userDao.findAllByRole(UserRole.WORKER);
        return users.stream()
                    .map(u -> userMapper.mapModelToShortDTO(u))
                    .collect(Collectors.toList());
    }

    public User findUserById(Long userId) {
        // to delete when sec will be added
        return userDao.findById(userId)
                      .orElse(((List<User>) userDao.findAll()).stream()
                                                              .findAny()
                                                              .get());
    }

    public List<User> setUsersFromDTO(ProjectDTO dto) {
        if (dto.getId() != null) {
            Project project = projectDao.findById(dto.getId())
                                        .orElseThrow(() -> new NoSuchElementException());
            List<Long> users = dto.getMembers()
                                  .stream()
                                  .map(m -> m.getId())
                                  .collect(Collectors.toList());
            List<User> membersOfProject = userDao.findAllByIdNotInAndProjects(users, project);
            membersOfProject.forEach(u -> {
                u.getProjects()
                 .remove(project);
            });
        }
        return dto.getMembers()
                  .stream()
                  .map(m -> findUserById(m.getId()))
                  .collect(Collectors.toList());
    }
}
