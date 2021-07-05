package com.fzcode.service.mail.controller;

import com.fzcode.internalcommon.dto.servicemail.common.MailDTO;
import com.fzcode.service.mail.flow.EmailFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/pri/email", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PriEmailController {


    private EmailFlow emailFlow;

    @Autowired
    public void setEmailFlow(EmailFlow emailFlow) {
        this.emailFlow = emailFlow;
    }

    @PostMapping(value = "/register/code")
    public Mono<String> register(@RequestBody @Validated MailDTO mailDTO) {
        return emailFlow.getStringFromRedis(mailDTO.getEmail(), "register");
    }

    @PostMapping(value = "/forget")
    public void forget(@RequestBody @Validated MailDTO mailDTO) {
//        return emailService.sendEmail(mailDTO.getEmail(), "forget");
    }
}
