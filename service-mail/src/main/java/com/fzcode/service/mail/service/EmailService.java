package com.fzcode.service.mail.service;

import com.fzcode.internalcommon.dto.http.SuccessResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@Component
public interface EmailService {
    public Mono<SuccessResponse>  sendEmail(String email, String type);
}
