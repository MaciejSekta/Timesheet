package com.msekta.timesheet.utils;

import com.msekta.timesheet.DTOs.PaymentInfoDTO;
import com.msekta.timesheet.DTOs.WorklogDTO;
import com.msekta.timesheet.DTOs.project.ProjectDTO;
import com.msekta.timesheet.DTOs.project.ProjectShortDTO;
import com.msekta.timesheet.DTOs.user.UserDTO;
import com.msekta.timesheet.DTOs.user.UserShortDTO;
import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.enums.UserType;
import com.msekta.timesheet.enums.WorklogStatus;
import com.msekta.timesheet.models.Project;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.models.Worklog;
import com.msekta.timesheet.repo.PaymentInfoDao;
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
    private PaymentInfoDao paymentInfoDao;

    @Autowired
    public Initialize(UserService userService, ProjectService projectService, WorklogService worklogService, UserDao userDao, PaymentInfoDao paymentInfoDao) {
        this.userService = userService;
        this.projectService = projectService;
        this.worklogService = worklogService;
        this.userDao = userDao;
        this.paymentInfoDao = paymentInfoDao;
    }

    @PostConstruct
    public void initializeDatabase() {
        users = new ArrayList<>();
        projects = new ArrayList<>();
        initializeUsers();
        initializeProjects();
//        initializeWorklogs();
    }

    private void initializeUsers() {
        PaymentInfoDTO paymentInfo = PaymentInfoDTO.builder()
                                                   .bankAccountNo("1234567")
                                                   .city("Pszczyna")
                                                   .houseNo("12")
                                                   .name("Adam")
                                                   .street("cos")
                                                   .postalCode("12-12")
                                                   .build();
        UserDTO userDto = UserDTO.userBuilder()
                                 .name("Adam")
                                 .surname("Nowak")
//                                 .email("masx.sekto@gmail.com")
                                 .birthday(LocalDate.now())
                                 .userType(UserType.INTERNAL.name())
                                 .workDayHours(8)
                                 .ratePerHour(100)
                                 .active(true)
                                 .paymentInfo(paymentInfo)
                                 .role(UserRole.WORKER.name())
                                 .build();
        User user = userService.createUser(userDto);
        user.setPassword("pass");
        user.setUsername("user");
        users.add(user);
        userDao.save(user);
        user.setSurname("user");
        user.setPassword("pass");
        userDao.save(user);
        PaymentInfoDTO paymentInfo1 = PaymentInfoDTO.builder()
                                                    .bankAccountNo("1234567")
                                                    .nip("0987654")
                                                    .city("Pszczyna")
                                                    .houseNo("12")
                                                    .name("Elon Company")
                                                    .street("cos")
                                                    .postalCode("12-12")
                                                    .build();
        UserDTO userDto1 = UserDTO.userBuilder()
                                  .name("Jan")
                                  .surname("Kowalski")
                                  .birthday(LocalDate.now())
                                  .ratePerHour(100)
                                  .userType(UserType.EXTERNAL.name())
                                  .workDayHours(8)
                                  .active(true)
                                  .role(UserRole.MANAGER.name())
                                  .paymentInfo(paymentInfo1)
                                  .build();
        User user1 = userService.createUser(userDto1);
        users.add(user1);
        userDao.save(user1);
        PaymentInfoDTO paymentInfo2 = PaymentInfoDTO.builder()
                                                    .bankAccountNo("1234567")
                                                    .city("Pszczyna")
                                                    .houseNo("12")
                                                    .name("Elon Company")
                                                    .street("cos")
                                                    .postalCode("12-12")
                                                    .build();
        UserDTO userDto2 = UserDTO.userBuilder()
                                  .name("Elon")
                                  .surname("Musk")
                                  .birthday(LocalDate.now())
                                  .ratePerHour(100)
                                  .userType(UserType.INTERNAL.name())
                                  .workDayHours(8)
                                  .active(true)
                                  .role(UserRole.MANAGER.name())
                                  .paymentInfo(paymentInfo2)
                                  .build();
        User user2 = userService.createUser(userDto2);
        users.add(user2);
        userDao.save(user2);
        PaymentInfoDTO paymentInfo3 = PaymentInfoDTO.builder()
                                                    .bankAccountNo("1234567")
                                                    .nip("123456")
                                                    .city("Pszczyna")
                                                    .houseNo("12")
                                                    .name(user.getSurname() + " " + user.getName())
                                                    .street("cos")
                                                    .postalCode("12-12")
                                                    .build();
        UserDTO userDto3 = UserDTO.userBuilder()
                                  .name("Mark")
                                  .surname("Zucc")
                                  .birthday(LocalDate.now())
                                  .ratePerHour(100)
                                  .userType(UserType.EXTERNAL.name())
                                  .workDayHours(8)
                                  .active(true)
                                  .role(UserRole.WORKER.name())
                                  .paymentInfo(paymentInfo3)
                                  .build();
        User user3 = userService.createUser(userDto3);
        users.add(user3);
        userDao.save(user3);
    }

    private void initializeProjects() {
        ProjectDTO project = ProjectDTO.builder()
                                       .active(true)
                                       .name("Security")
                                       .manager(UserShortDTO.builder()
                                                            .id(userDao.findAllByRole(UserRole.MANAGER)
                                                                       .stream()
                                                                       .findAny()
                                                                       .get()
                                                                       .getId())
                                                            .build())
                                       .members(Arrays.asList(UserShortDTO.builder()
                                                                          .id(userDao.findAllByRole(UserRole.WORKER)
                                                                                     .stream()
                                                                                     .findAny()
                                                                                     .get()
                                                                                     .getId())
                                                                          .build()))
                                       .build();
        projects.add(projectService.createProject(project));
        ProjectDTO project1 = ProjectDTO.builder()
                                        .active(true)
                                        .name("Frontend")
                                        .manager(UserShortDTO.builder()
                                                             .id(userDao.findAllByRole(UserRole.MANAGER)
                                                                        .stream()
                                                                        .findAny()
                                                                        .get()
                                                                        .getId())
                                                             .build())
                                        .members(Arrays.asList(UserShortDTO.builder()
                                                                           .id(userDao.findAllByRole(UserRole.WORKER)
                                                                                      .stream()
                                                                                      .findAny()
                                                                                      .get()
                                                                                      .getId())
                                                                           .build()))
                                        .build();
        projects.add(projectService.createProject(project1));
        ProjectDTO project2 = ProjectDTO.builder()
                                        .active(true)
                                        .name("Backend")
                                        .manager(UserShortDTO.builder()
                                                             .id(userDao.findAllByRole(UserRole.MANAGER)
                                                                        .stream()
                                                                        .findAny()
                                                                        .get()
                                                                        .getId())
                                                             .build())
                                        .members(Arrays.asList(UserShortDTO.builder()
                                                                           .id(userDao.findAllByRole(UserRole.WORKER)
                                                                                      .stream()
                                                                                      .findAny()
                                                                                      .get()
                                                                                      .getId())
                                                                           .build()))
                                        .build();
        projects.add(projectService.createProject(project2));
        ProjectDTO project3 = ProjectDTO.builder()
                                        .active(true)
                                        .name("Planning")
                                        .manager(UserShortDTO.builder()
                                                             .id(userDao.findAllByRole(UserRole.MANAGER)
                                                                        .stream()
                                                                        .findAny()
                                                                        .get()
                                                                        .getId())
                                                             .build())
                                        .members(Arrays.asList(UserShortDTO.builder()
                                                                           .id(userDao.findAllByRole(UserRole.WORKER)
                                                                                      .stream()
                                                                                      .findAny()
                                                                                      .get()
                                                                                      .getId())
                                                                           .build()))
                                        .build();
        projects.add(projectService.createProject(project3));
    }

    private void initializeWorklogs() {
        WorklogDTO worklog = WorklogDTO.builder()
                                       .comment("comm")
                                       .date(LocalDate.now())
                                       .hourFrom(8)
                                       .hourTo(16)
                                       .duration(8)
                                       .project(ProjectShortDTO.builder()
                                                               .id(projects.get(1)
                                                                           .getId())
                                                               .build())
                                       .user(UserShortDTO.builder()
                                                         .id(users.get(1)
                                                                  .getId())
                                                         .build())
                                       .status(WorklogStatus.ACCEPTED.name())
                                       .build();
        Worklog worklog1 = worklogService.createWorklog(worklog);
        User user = userService.findUserById(users.get(1)
                                                  .getId());
        user.getWorklogs()
            .add(worklog1);
        userDao.save(user);
        WorklogDTO worklog2 = WorklogDTO.builder()
                                        .comment("comm")
                                        .date(LocalDate.now())
                                        .hourFrom(8)
                                        .hourTo(16)
                                        .duration(8)
                                        .project(ProjectShortDTO.builder()
                                                                .id(projects.get(1)
                                                                            .getId())
                                                                .build())
                                        .user(UserShortDTO.builder()
                                                          .id(users.get(1)
                                                                   .getId())
                                                          .build())
                                        .status(WorklogStatus.PENDING.name())
                                        .build();
        Worklog worklog3 = worklogService.createWorklog(worklog2);
        user.getWorklogs()
            .add(worklog3);
        userDao.save(user);
        WorklogDTO worklog4 = WorklogDTO.builder()
                                        .comment("comm")
                                        .date(LocalDate.now())
                                        .hourFrom(8)
                                        .hourTo(16)
                                        .duration(8)
                                        .project(ProjectShortDTO.builder()
                                                                .id(projects.get(1)
                                                                            .getId())
                                                                .build())
                                        .user(UserShortDTO.builder()
                                                          .id(users.get(1)
                                                                   .getId())
                                                          .build())
                                        .status(WorklogStatus.ACCEPTED.name())
                                        .build();
        Worklog worklog5 = worklogService.createWorklog(worklog4);
        user.getWorklogs()
            .add(worklog5);
        userDao.save(user);
        WorklogDTO worklog6 = WorklogDTO.builder()
                                        .comment("comm")
                                        .date(LocalDate.now())
                                        .hourFrom(8)
                                        .hourTo(16)
                                        .duration(8)
                                        .project(ProjectShortDTO.builder()
                                                                .id(projects.get(0)
                                                                            .getId())
                                                                .build())
                                        .user(UserShortDTO.builder()
                                                          .id(users.get(1)
                                                                   .getId())
                                                          .build())
                                        .status(WorklogStatus.PENDING.name())
                                        .build();
        Worklog worklog7 = worklogService.createWorklog(worklog6);
        user.getWorklogs()
            .add(worklog7);
        userDao.save(user);
        WorklogDTO worklog8 = WorklogDTO.builder()
                                        .comment("comm")
                                        .date(LocalDate.now())
                                        .hourFrom(8)
                                        .hourTo(16)
                                        .duration(8)
                                        .project(ProjectShortDTO.builder()
                                                                .id(projects.get(1)
                                                                            .getId())
                                                                .build())
                                        .user(UserShortDTO.builder()
                                                          .id(users.get(0)
                                                                   .getId())
                                                          .build())
                                        .status(WorklogStatus.REJECTED.name())
                                        .build();
        Worklog worklog9 = worklogService.createWorklog(worklog8);
        user.getWorklogs()
            .add(worklog9);
        userDao.save(user);
    }
}
