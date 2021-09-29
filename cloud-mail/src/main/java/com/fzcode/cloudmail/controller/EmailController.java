package com.fzcode.cloudmail.controller;

import com.fzcode.internalcommon.dto.cloudmail.request.RegisterCodeRequest;
import com.fzcode.internalcommon.dto.cloudmail.common.MailDTO;
import com.fzcode.cloudmail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class EmailController {


    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/register")
    public Mono<String> register(@RequestBody @Validated RegisterCodeRequest registerCodeRequest) {
//        return emailService.sendEmail(mailDTO.getEmail(), "register");
        return emailService.sendEmail(registerCodeRequest.getEmail(), "register");
    }

    @PostMapping(value = "/forget")
    public Mono<String> forget(@RequestBody @Validated MailDTO mailDTO) {
        return emailService.sendEmail(mailDTO.getEmail(), "forget");
    }
}
