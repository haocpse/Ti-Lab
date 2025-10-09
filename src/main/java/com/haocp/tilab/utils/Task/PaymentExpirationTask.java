package com.haocp.tilab.utils.Task;

import com.haocp.tilab.entity.Payment;
import com.haocp.tilab.enums.PaymentStatus;
import com.haocp.tilab.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentExpirationTask {

    private final PaymentRepository paymentRepository;

//    @Scheduled(fixedRate = 60000)
//    public void checkAndExpirePayments() {
//        Instant now = Instant.now();
//
//        List<Payment> payments = paymentRepository
//                .findPaymentByStatus(PaymentStatus.PROCESSING);
//
//        for (Payment payment : payments) {
//            if (payment.getUpdatedAt().plus(15, ChronoUnit.MINUTES).isBefore(now)){
//                payment.setStatus(PaymentStatus.FAILED);
//            }
//        }
//        paymentRepository.saveAll(payments);
//    }

}
