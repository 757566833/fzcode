package com.fzcode.mailservice.service;

import com.fzcode.mailservice.exception.CustomizeException;
import com.fzcode.mailservice.http.ResponseDTO;
import com.fzcode.mailservice.util.CharUtil;
import com.fzcode.mailservice.util.MailUtil;
import com.fzcode.mailservice.util.RedisUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmailService {
    public Mono<Boolean> sendEmail(String email, String checkCode) throws CustomizeException {
        Mono<Boolean> mailMono = MailUtil.sendMail(email, checkCode);
        return mailMono;
    }
}
