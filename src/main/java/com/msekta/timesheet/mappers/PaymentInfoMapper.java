package com.msekta.timesheet.mappers;

import com.msekta.timesheet.DTOs.PaymentInfoDTO;
import com.msekta.timesheet.models.PaymentInfo;
import com.msekta.timesheet.models.User;
import org.springframework.stereotype.Component;

@Component
public class PaymentInfoMapper {

    public PaymentInfoDTO mapModelDoDTO(PaymentInfo model) {
        return PaymentInfoDTO.builder()
                             .id(model.getId())
                             .bankAccountNo(model.getBankAccountNo())
                             .city(model.getCity())
                             .name(model.getName())
                             .houseNo(model.getHouseNo())
                             .nip(model.getNip())
                             .street(model.getStreet())
                             .postalCode(model.getPostalCode())
                             .build();
    }

    public PaymentInfo mapDTOToModel(PaymentInfoDTO dto, User user) {
        return PaymentInfo.builder()
                          .id(dto.getId())
                          .bankAccountNo(dto.getBankAccountNo())
                          .city(dto.getCity())
                          .name(dto.getName())
                          .houseNo(dto.getHouseNo())
                          .nip(dto.getNip())
                          .street(dto.getStreet())
                          .postalCode(dto.getPostalCode())
                          .user(user)
                          .build();
    }
}
