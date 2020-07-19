package com.msekta.timesheet.services;

import com.msekta.timesheet.DTOs.PaymentInfoDTO;
import com.msekta.timesheet.mappers.PaymentInfoMapper;
import com.msekta.timesheet.models.PaymentInfo;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.repos.PaymentInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PaymentInfoService {

    private PaymentInfoMapper paymentInfoMapper;

    private PaymentInfoDAO paymentInfoDao;

    @Autowired
    public PaymentInfoService(PaymentInfoMapper paymentInfoMapper, PaymentInfoDAO paymentInfoDao) {
        this.paymentInfoMapper = paymentInfoMapper;
        this.paymentInfoDao = paymentInfoDao;
    }

    public PaymentInfo mapDTOToModel(PaymentInfoDTO dto, User user) {
        if (dto.getId() != null) {
            paymentInfoDao.findById(dto.getId())
                          .orElseThrow(() -> new NoSuchElementException());
            return paymentInfoDao.save(paymentInfoMapper.mapDTOToModel(dto, user));
        }
        return paymentInfoDao.save(paymentInfoMapper.mapDTOToModel(dto, user));
    }

    public PaymentInfoDTO mapModelToDTO(PaymentInfo model) {
        return paymentInfoMapper.mapModelDoDTO(model);
    }
}
