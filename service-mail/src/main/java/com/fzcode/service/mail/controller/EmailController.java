package com.fzcode.service.mail.controller;

import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicemail.common.MailDTO;
import com.fzcode.service.mail.service.EmailFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class EmailController {


    private EmailFlow emailFlow;

    @Autowired
    public void setEmailFlow(EmailFlow emailFlow) {
        this.emailFlow = emailFlow;
    }

    @PostMapping(value = "/register")
    public Mono<SuccessResponse> register(@RequestBody @Validated MailDTO mailDTO) {
//        return emailService.sendEmail(mailDTO.getEmail(), "register");
        return emailFlow.sendEmail(mailDTO.getEmail(), "register");
    }

    @PostMapping(value = "/forget")
    public void forget(@RequestBody @Validated MailDTO mailDTO) {
//        return emailService.sendEmail(mailDTO.getEmail(), "forget");
    }
}
