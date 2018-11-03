package com.msekta.timesheet.mappers;

import com.msekta.timesheet.DTOs.user.UserDTO;
import com.msekta.timesheet.DTOs.user.UserDetailsDTO;
import com.msekta.timesheet.DTOs.user.UserShortDTO;
import com.msekta.timesheet.enums.UserRole;
import com.msekta.timesheet.enums.UserType;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.services.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private PaymentInfoService paymentInfoService;

    public UserDTO mapModelToDTO(User model) {
        UserDTO dto = UserDTO.userBuilder()
                             .id(model.getId())
                             .name(model.getName())
                             .surname(model.getSurname())
                             .birthday(model.getBirthday())
                             .role(model.getRole().getName())
                             .userType(model.getUserType().getName())
                             .workDayHours(model.getWorkDayHours())
                             .ratePerHour(model.getRatePerHour())
                             .active(model.getActive())
                             .paymentInfo(paymentInfoService.mapModelToDTO(model.getPaymentInfo()))
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
        model.setRatePerHour(dto.getRatePerHour());
        model.setUserType(UserType.getEnum(dto.getUserType()));
        model.setPaymentInfo(paymentInfoService.mapDTOToModel(dto.getPaymentInfo(), model));
        model.setRole(UserRole.getEnum(dto.getRole()));
        return model;
    }
}
