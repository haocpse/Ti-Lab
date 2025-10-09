package com.haocp.tilab.utils.Task;

import com.haocp.tilab.entity.Payment;
import com.haocp.tilab.entity.VerificationToken;
import com.haocp.tilab.enums.PaymentStatus;
import com.haocp.tilab.repository.PaymentRepository;
import com.haocp.tilab.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenExpirationTask {

    private final VerificationTokenRepository verificationTokenRepository;

//    @Scheduled(fixedRate = 60000)
//    public void checkAndDeleteExpiredTokens() {
//        Instant now = Instant.now();
//        List<VerificationToken> tokens = verificationTokenRepository
//                .findByExpiredAtBefore(now);
//        verificationTokenRepository.deleteAll(tokens);
//    }

}
