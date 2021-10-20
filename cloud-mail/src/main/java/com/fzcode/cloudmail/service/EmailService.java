package com.fzcode.cloudmail.service;

import com.fzcode.cloudmail.exception.CustomizeException;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@Service
public interface EmailService {
    public String  sendEmail(String email, String type) throws CustomizeException;
}
