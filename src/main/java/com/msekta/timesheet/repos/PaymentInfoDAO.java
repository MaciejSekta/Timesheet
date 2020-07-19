package com.msekta.timesheet.repos;

import com.msekta.timesheet.models.PaymentInfo;
import org.springframework.data.repository.CrudRepository;

public interface PaymentInfoDAO extends CrudRepository<PaymentInfo, Long> {
}
