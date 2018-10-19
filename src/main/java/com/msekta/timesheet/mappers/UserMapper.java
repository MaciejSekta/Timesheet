package com.msekta.timesheet.mappers;

import com.msekta.timesheet.DTOs.user.UserDTO;
import com.msekta.timesheet.DTOs.user.UserShortDTO;
import com.msekta.timesheet.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO mapModelToDTO(User user) {
        UserDTO dto = UserDTO.builder()
                             .id(user.getId())
                             .name(user.getName())
                             .surname(user.getSurname())
                             .bithday(user.getBithday())
                             .role(user.getRole()
                                       .name())
                             .userType(user.getUserType()
                                           .name())
                             .workDayHours(user.getWorkDayHours())
                             .build();
        return dto;
    }

    public UserShortDTO mapModelToShortDTO(User user) {
        return UserShortDTO.builder()
                           .id(user.getId())
                           .name(user.getName())
                           .surname(user.getSurname())
                           .build();
    }
}
