package com.haocp.tilab.utils.listener;

import com.haocp.tilab.entity.VerificationToken;
import com.haocp.tilab.enums.TokenType;
import com.haocp.tilab.service.VerificationTokenService;
import com.haocp.tilab.utils.event.GenerateTokenLoginEvent;
import com.haocp.tilab.utils.event.OrderCreatedEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationTokenListener {

    VerificationTokenService verificationTokenService;

    public VerificationTokenListener(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    @EventListener
    public void handleGenerateTokenLoginEvent(GenerateTokenLoginEvent event) {
        verificationTokenService.createRefreshToken(event.getJwtId(), event.getUser(), event.getRefreshDuration());
    }

}
