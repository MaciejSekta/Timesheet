package com.msekta.timesheet.services;

import com.msekta.timesheet.DTOs.StatsDTO;
import com.msekta.timesheet.enums.WorklogStatus;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.models.Worklog;
import com.msekta.timesheet.repo.ProjectDao;
import com.msekta.timesheet.repo.UserDao;
import com.msekta.timesheet.repo.WorklogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class StatsService {

    private UserDao userDao;

    private ProjectDao projectDao;

    private WorklogDao worklogDao;

    @Autowired
    public StatsService(UserDao userDao, ProjectDao projectDao, WorklogDao worklogDao) {
        this.userDao = userDao;
        this.projectDao = projectDao;
        this.worklogDao = worklogDao;
    }

    public List<StatsDTO> getWeekHours(Long userId) {
        User user = userDao.findById(userId)
                           .orElse(userDao.findById(3L)
                                          .orElseThrow(() -> new NoSuchElementException()));
        List<StatsDTO> statsList = new ArrayList<>();
        LocalDate monday = LocalDate.now()
                                    .with(DayOfWeek.MONDAY);
        LocalDate friday = LocalDate.now()
                                    .with(DayOfWeek.FRIDAY);
        getWorklogsBetweenDatesAndAddToList(monday, friday, user, statsList);
        return statsList;
    }

    public List<StatsDTO> getMonthHours(Long userId) {
        User user = userDao.findById(userId)
                           .orElse(userDao.findById(3L)
                                          .orElseThrow(() -> new NoSuchElementException()));
        List<StatsDTO> statsList = new ArrayList<>();
        LocalDate firstDayOfMonth = LocalDate.now()
                                             .withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now()
                                            .withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        getWorklogsBetweenDatesAndAddToList(firstDayOfMonth, lastDayOfMonth, user, statsList);
        return statsList;
    }

    public List<StatsDTO> getYearHoursByMonth(Long userId) {
        User user = userDao.findById(userId)
                           .orElse(userDao.findById(3L)
                                          .orElseThrow(() -> new NoSuchElementException()));
        List<StatsDTO> statsList = new ArrayList<>();
        LocalDate firstDayOfMonth = LocalDate.now()
                                             .withDayOfMonth(1);
        LocalDate previouYear = firstDayOfMonth.minusMonths(11);
        for (int month = 1; month <= 12; month++) {
            LocalDate firstDay = previouYear;
            LocalDate lastDay = LocalDate.of(firstDay.getYear(), firstDay.getMonth(), firstDay.lengthOfMonth());
            previouYear = previouYear.plusMonths(1);
            List<Worklog> worklogs = worklogDao.findAllByUser_idAndDateBetween(user.getId(), firstDay, lastDay);
            StatsDTO statsForMonth = StatsDTO.builder()
                                             .name(lastDay.getMonth()
                                                          .getDisplayName(TextStyle.SHORT, Locale.ENGLISH))
                                             .value(worklogs.stream()
                                                            .mapToInt(w -> w.getDuration())
                                                            .sum())
                                             .build();
            statsList.add(statsForMonth);
        }
        return statsList;
    }

    private void getWorklogsBetweenDatesAndAddToList(LocalDate min, LocalDate max, User user, List<StatsDTO> statsList) {
        for (LocalDate date = min; date.isBefore(max.plusDays(1)); date = date.plusDays(1)) {
            List<Worklog> worklogsForDay = worklogDao.findAllByUserAndDate(user, date);
            StatsDTO statsForDay = StatsDTO.builder()
                                           .name(date.toString())
                                           .value(worklogsForDay.stream()
                                                                .mapToInt(w -> w.getDuration())
                                                                .sum())
                                           .build();
            statsList.add(statsForDay);
        }
    }

    public List<StatsDTO> getWeekProjects(Long userId) {
        User user = userDao.findById(userId)
                           .orElse(userDao.findById(3L)
                                          .orElseThrow(() -> new NoSuchElementException()));
        List<StatsDTO> statsList = new ArrayList<>();
        LocalDate monday = LocalDate.now()
                                    .with(DayOfWeek.MONDAY);
        LocalDate friday = LocalDate.now()
                                    .with(DayOfWeek.FRIDAY);
        List<Worklog> worklogs = worklogDao.findAllByUser_idAndDateBetween(user.getId(), monday, friday);
        Map<String, Integer> hoursPerProject = new HashMap<>();
        getWorklogsGroupedByProjectsAndAddToList(worklogs, hoursPerProject, statsList);
        return statsList;
    }

    public List<StatsDTO> getMonthProjects(Long userId) {
        User user = userDao.findById(userId)
                           .orElse(userDao.findById(3L)
                                          .orElseThrow(() -> new NoSuchElementException()));
        List<StatsDTO> statsList = new ArrayList<>();
        LocalDate firstDayOfMonth = LocalDate.now()
                                             .withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now()
                                            .withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        List<Worklog> worklogs = worklogDao.findAllByUser_idAndDateBetween(user.getId(), firstDayOfMonth, lastDayOfMonth);
        Map<String, Integer> hoursPerProject = new HashMap<>();
        getWorklogsGroupedByProjectsAndAddToList(worklogs, hoursPerProject, statsList);
        return statsList;
    }

    public List<StatsDTO> getYearProjects(Long userId) {
        User user = userDao.findById(userId)
                           .orElse(userDao.findById(3L)
                                          .orElseThrow(() -> new NoSuchElementException()));
        List<StatsDTO> statsList = new ArrayList<>();
        LocalDate firstDayOfMonth = LocalDate.now()
                                             .withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now()
                                            .withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        LocalDate previouYear = firstDayOfMonth.minusMonths(11);
        List<Worklog> worklogs = worklogDao.findAllByUser_idAndDateBetween(user.getId(), previouYear, lastDayOfMonth);
        Map<String, Integer> hoursPerProject = new HashMap<>();
        getWorklogsGroupedByProjectsAndAddToList(worklogs, hoursPerProject, statsList);
        return statsList;
    }

    private void getWorklogsGroupedByProjectsAndAddToList(List<Worklog> worklogs, Map<String, Integer> hoursPerProject, List<StatsDTO> statsList) {
        worklogs.forEach(w -> {
            if (hoursPerProject.containsKey(w.getProject()
                                             .getName())) {
                Integer sum = hoursPerProject.get(w.getProject()
                                                   .getName()) + w.getDuration();
                hoursPerProject.put(w.getProject()
                                     .getName(), sum);
            } else {
                hoursPerProject.put(w.getProject()
                                     .getName(), w.getDuration());
            }
        });
        for (Map.Entry<String, Integer> project : hoursPerProject.entrySet()) {
            statsList.add(StatsDTO.builder()
                                  .name(project.getKey())
                                  .value(project.getValue())
                                  .build());
        }
    }

    public List<StatsDTO> getWeekWorklogsStatus(Long userId) {
        User user = userDao.findById(userId)
                           .orElse(userDao.findById(3L)
                                          .orElseThrow(() -> new NoSuchElementException()));
        LocalDate monday = LocalDate.now()
                                    .with(DayOfWeek.MONDAY);
        LocalDate friday = LocalDate.now()
                                    .with(DayOfWeek.FRIDAY);
        return getWorklogsGroupedByStatusAndAddToList(monday, friday, user);

    }

    public List<StatsDTO> getMonthWorklogsStatus(Long userId) {
        User user = userDao.findById(userId)
                           .orElse(userDao.findById(3L)
                                          .orElseThrow(() -> new NoSuchElementException()));
        LocalDate firstDayOfMonth = LocalDate.now()
                                             .withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now()
                                            .withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        return getWorklogsGroupedByStatusAndAddToList(firstDayOfMonth, lastDayOfMonth, user);
    }

    public List<StatsDTO> getYearWorklogsStatus(Long userId) {
        User user = userDao.findById(userId)
                           .orElse(userDao.findById(3L)
                                          .orElseThrow(() -> new NoSuchElementException()));
        LocalDate firstDayOfMonth = LocalDate.now()
                                             .withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now()
                                            .withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        LocalDate previouYear = firstDayOfMonth.minusMonths(11);
        return getWorklogsGroupedByStatusAndAddToList(previouYear, lastDayOfMonth, user);
    }

    private StatsDTO getCountOfWorklogsWithStatus(WorklogStatus status, User user, LocalDate min, LocalDate max) {
        return StatsDTO.builder()
                       .name(status.getName())
                       .value(Math.toIntExact(worklogDao.countAllByStatusAndUserAndDateBetween(status, user, min, max)))
                       .build();
    }

    private List<StatsDTO> getWorklogsGroupedByStatusAndAddToList(LocalDate min, LocalDate max, User user) {
        StatsDTO statsOfAccepted = getCountOfWorklogsWithStatus(WorklogStatus.ACCEPTED, user, min, max);
        StatsDTO statsOfPending = getCountOfWorklogsWithStatus(WorklogStatus.PENDING, user, min, max);
        StatsDTO statsOfRejected = getCountOfWorklogsWithStatus(WorklogStatus.REJECTED, user, min, max);
        return Arrays.asList(statsOfAccepted, statsOfPending, statsOfRejected);
    }
}
