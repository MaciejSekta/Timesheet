package com.msekta.timesheet.repo;

import com.msekta.timesheet.models.PaymentInfo;
import org.springframework.data.repository.CrudRepository;

public interface PaymentInfoDao extends CrudRepository<PaymentInfo, Long> {
}
