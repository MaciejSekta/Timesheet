package com.msekta.timesheet.mappers;

import com.msekta.timesheet.DTOs.user.UserDTO;
import com.msekta.timesheet.DTOs.user.UserDetailsDTO;
import com.msekta.timesheet.DTOs.user.UserShortDTO;
import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.enums.UserType;
import com.msekta.timesheet.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO mapModelToDTO(User model) {
        UserDTO dto = UserDTO.userBuilder()
                             .id(model.getId())
                             .name(model.getName())
                             .surname(model.getSurname())
                             .birthday(model.getBirthday())
                             .role(model.getRole().name())
                             .userType(model.getUserType().name())
                             .workDayHours(model.getWorkDayHours())
                             .build();
        return dto;
    }

    public UserShortDTO mapModelToShortDTO(User model) {
        return UserShortDTO.builder()
                           .id(model.getId())
                           .name(model.getName())
                           .surname(model.getSurname())
                           .build();
    }

    public User mapDTOToModel(UserDTO dto, User model) {
        model.setName(dto.getName());
        model.setSurname(dto.getSurname());
        model.setActive(dto.getActive());
        model.setBirthday(dto.getBirthday());
        model.setWorkDayHours(dto.getWorkDayHours());
        model.setUserType(UserType.valueOf(dto.getUserType()));
        model.setRole(UserRole.valueOf(dto.getRole()));
        return model;
    }
}
