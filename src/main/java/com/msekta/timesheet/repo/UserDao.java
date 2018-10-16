package com.msekta.timesheet.repo;

import com.msekta.timesheet.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
}
