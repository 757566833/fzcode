package com.fzcode.mailservice.flow;

import com.fzcode.mailservice.exception.CustomizeException;
import com.fzcode.mailservice.http.ResponseDTO;
import com.fzcode.mailservice.service.EmailService;
import com.fzcode.mailservice.util.CharUtil;
import com.fzcode.mailservice.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;


@Component
public class EmailFlow {
    private EmailService emailService;

    @Autowired
    public void setUserDao(EmailService emailService) {
        this.emailService = emailService;
    }

    public Mono<ResponseDTO> sendEmail(String email, String type) {
        String checkCode = CharUtil.getCharAndNumber(6).toUpperCase();
        System.out.println(checkCode);
        return Mono.create(sink -> {
            Mono<Boolean> redisMono = RedisUtil.setString(email + ":" + type, checkCode, 600);
            redisMono.subscribe(bool -> {
                System.out.println("redis结果" + bool.toString());
                try {
                    this.sendEmailMono(bool, email, checkCode, sink);
                } catch (CustomizeException e) {
                    e.printStackTrace();
                }
            });

        });
    }

    public Mono<Boolean> sendEmailMono(Boolean bool, String email, String checkCode, MonoSink<ResponseDTO> sink) throws CustomizeException {
        if (!bool) {
            throw new CustomizeException("redis不可用");
        }
        Mono<Boolean> emailMono = emailService.sendEmail(email, checkCode);
        emailMono.subscribe(l -> {
            try {
                this.result(l, sink);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return emailMono;

    }

    public void result(Boolean bool, MonoSink<ResponseDTO> sink) throws CustomizeException {
        if (!bool) {
            throw new CustomizeException("email发送失败");
        }
        sink.success(new ResponseDTO("200", "", "success"));
    }

    public Mono<String> getStringFromRedis(String email, String type) {
        return RedisUtil.getString(email + ":" + type);
    }

}
