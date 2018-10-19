package com.msekta.timesheet.utils;

import com.msekta.timesheet.DTOs.WorklogDTO;
import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.DTOs.project.ProjectShortDTO;
import com.msekta.timesheet.DTOs.user.UserDTO;
import com.msekta.timesheet.DTOs.user.UserShortDTO;
import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.enums.UserType;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.models.Worklog;
import com.msekta.timesheet.repo.UserDao;
import com.msekta.timesheet.services.ProjectService;
import com.msekta.timesheet.services.UserService;
import com.msekta.timesheet.services.WorklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Initialize {

    private UserService userService;
    private ProjectService projectService;
    private WorklogService worklogService;
    private UserDao userDao;
    private List<User> users;
    private List<Project> projects;

    @Autowired
    public Initialize(UserService userService, ProjectService projectService, WorklogService worklogService, UserDao userDao) {
        this.userService = userService;
        this.projectService = projectService;
        this.worklogService = worklogService;
        this.userDao = userDao;
    }

    @PostConstruct
    public void initializeDatabase() {
        users = new ArrayList<>();
        projects = new ArrayList<>();
        initializeUsers();
        initializeProjects();
        initializeWorklogs();
    }

    private void initializeUsers() {
        UserDTO user = UserDTO.userBuilder()
                              .name("User")
                              .surname("1")
                              .birthday(LocalDate.now())
                              .userType(UserType.INTERNAL.name())
                              .workDayHours(8)
                              .active(true)
                              .role(UserRole.ADMIN.name())
                              .build();
        users.add(userService.createUser(user));
        UserDTO user1 = UserDTO.userBuilder()
                               .name("User")
                               .surname("2")
                               .birthday(LocalDate.now())
                               .userType(UserType.EXTERNAL.name())
                               .workDayHours(8)
                               .active(true)
                               .role(UserRole.MANAGER.name())
                               .build();
        users.add(userService.createUser(user1));
        UserDTO user2 = UserDTO.userBuilder()
                               .name("User")
                               .surname("3")
                               .birthday(LocalDate.now())
                               .userType(UserType.INTERNAL.name())
                               .workDayHours(8)
                               .active(true)
                               .role(UserRole.WORKER.name())
                               .build();
        users.add(userService.createUser(user2));
        UserDTO user3 = UserDTO.userBuilder()
                               .name("User")
                               .surname("4")
                               .birthday(LocalDate.now())
                               .userType(UserType.EXTERNAL.name())
                               .workDayHours(8)
                               .active(true)
                               .role(UserRole.WORKER.name())
                               .build();
        users.add(userService.createUser(user3));
    }

    private void initializeProjects() {
        ProjectDTO project = ProjectDTO.builder()
                                       .active(true)
                                       .name("Project1")
                                       .manager(UserShortDTO.builder().id(1L).build())
                                       .members(Arrays.asList(UserShortDTO.builder().id(1L).build(),
                                                               UserShortDTO.builder().id(2L).build()))
                                       .build();
        projects.add(projectService.createProject(project));
        ProjectDTO project1 = ProjectDTO.builder()
                                        .active(true)
                                        .name("Project2")
                                        .manager(UserShortDTO.builder().id(2L).build())
                                        .members(Arrays.asList(UserShortDTO.builder().id(2L).build(),
                                                                UserShortDTO.builder().id(3L).build()))
                                        .build();
        projects.add(projectService.createProject(project1));
        ProjectDTO project2 = ProjectDTO.builder()
                                        .active(true)
                                        .name("Project3")
                                        .manager(UserShortDTO.builder().id(3L).build())
                                        .members(Arrays.asList(UserShortDTO.builder().id(1L).build(),
                                                                UserShortDTO.builder().id(2L).build()))
                                        .build();
        projects.add(projectService.createProject(project2));
        ProjectDTO project3 = ProjectDTO.builder()
                                        .active(true)
                                        .name("Project4")
                                        .manager(UserShortDTO.builder().id(1L).build())
                                        .members(Arrays.asList(UserShortDTO.builder().id(2L).build(),
                                                                UserShortDTO.builder().id(3L).build()))
                                        .build();
        projects.add(projectService.createProject(project3));
    }

    public void initializeWorklogs() {
        WorklogDTO worklog = WorklogDTO.builder()
                                       .comment("comm")
                                       .date(LocalDate.now())
                                       .hourFrom(8)
                                       .hourTo(16)
                                       .duration(8)
                                       .project(ProjectShortDTO.builder().id(projects.get(1).getId()).build())
                                       .user(UserShortDTO.builder().id(users.get(1) .getId()) .build())
                                       .build();
        Worklog worklog1 = worklogService.createWorklog(worklog);
        User user = userService.findUserById(users.get(1).getId());
        user.getWorklogs().add(worklog1);
        userDao.save(user);
        WorklogDTO worklog2 = WorklogDTO.builder()
                                       .comment("comm")
                                       .date(LocalDate.now())
                                       .hourFrom(8)
                                       .hourTo(16)
                                       .duration(8)
                                       .project(ProjectShortDTO.builder().id(projects.get(1).getId()).build())
                                       .user(UserShortDTO.builder().id(users.get(1) .getId()) .build())
                                       .build();
        Worklog worklog3 = worklogService.createWorklog(worklog2);
        user.getWorklogs().add(worklog3);
        userDao.save(user);
        WorklogDTO worklog4 = WorklogDTO.builder()
                                       .comment("comm")
                                       .date(LocalDate.now())
                                       .hourFrom(8)
                                       .hourTo(16)
                                       .duration(8)
                                       .project(ProjectShortDTO.builder().id(projects.get(1).getId()).build())
                                       .user(UserShortDTO.builder().id(users.get(1) .getId()) .build())
                                       .build();
        Worklog worklog5 = worklogService.createWorklog(worklog4);
        user.getWorklogs().add(worklog5);
        userDao.save(user);
        WorklogDTO worklog6 = WorklogDTO.builder()
                                       .comment("comm")
                                       .date(LocalDate.now())
                                       .hourFrom(8)
                                       .hourTo(16)
                                       .duration(8)
                                       .project(ProjectShortDTO.builder().id(projects.get(1).getId()).build())
                                       .user(UserShortDTO.builder().id(users.get(1) .getId()) .build())
                                       .build();
        Worklog worklog7 = worklogService.createWorklog(worklog6);
        user.getWorklogs().add(worklog7);
        userDao.save(user);
        WorklogDTO worklog8 = WorklogDTO.builder()
                                       .comment("comm")
                                       .date(LocalDate.now())
                                       .hourFrom(8)
                                       .hourTo(16)
                                       .duration(8)
                                       .project(ProjectShortDTO.builder().id(projects.get(1).getId()).build())
                                       .user(UserShortDTO.builder().id(users.get(1) .getId()) .build())
                                       .build();
        Worklog worklog9 = worklogService.createWorklog(worklog8);
        user.getWorklogs().add(worklog9);
        userDao.save(user);
    }
}
