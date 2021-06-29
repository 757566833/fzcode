package com.fzcode.service.mail.service;

import com.fzcode.service.mail.exception.CustomizeException;
import com.fzcode.service.mail.util.MailUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmailService {
    public Mono<Boolean> sendEmail(String email, String checkCode) throws CustomizeException {
        Mono<Boolean> mailMono = MailUtil.sendMail(email, checkCode);
        return mailMono;
    }
}
