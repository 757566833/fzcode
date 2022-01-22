package com.fzcode.cloudmail.controller;

import com.fzcode.internalcommon.dto.cloudmail.request.RegisterCodeRequest;
import com.fzcode.internalcommon.dto.cloudmail.common.MailDTO;
import com.fzcode.cloudmail.service.EmailService;
import com.fzcode.internalcommon.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class EmailController {


    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/register")
    public String register(@RequestBody @Validated RegisterCodeRequest registerCodeRequest) throws CustomizeException {
//        return emailService.sendEmail(mailDTO.getEmail(), "register");
        return emailService.sendEmail(registerCodeRequest.getEmail(), "register");
    }

    @PostMapping(value = "/forget")
    public String forget(@RequestBody @Validated MailDTO mailDTO) throws CustomizeException {
        return emailService.sendEmail(mailDTO.getEmail(), "forget");
    }
}
