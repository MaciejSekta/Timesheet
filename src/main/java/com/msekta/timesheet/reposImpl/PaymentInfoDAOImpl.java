package com.msekta.timesheet.reposImpl;

import com.msekta.timesheet.models.PaymentInfo;
import com.msekta.timesheet.repos.PaymentInfoDAO;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class PaymentInfoDAOImpl extends SimpleJpaRepository<PaymentInfo, Long> implements PaymentInfoDAO {

    private final EntityManager entityManager;

    public PaymentInfoDAOImpl(Class<PaymentInfo> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }
}
