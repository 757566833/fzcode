package com.fzcode.cloudmail.service;

import com.fzcode.internalcommon.dto.http.SuccessResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@Service
public interface EmailService {
    public Mono<String>  sendEmail(String email, String type);
}
