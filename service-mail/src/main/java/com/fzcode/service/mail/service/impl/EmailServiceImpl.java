package com.fzcode.service.mail.service.impl;

import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.service.mail.exception.CustomizeException;
import com.fzcode.service.mail.service.EmailService;
import com.fzcode.service.mail.util.CharUtil;
import com.fzcode.service.mail.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import javax.mail.internet.MimeMessage;

public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSenderImpl javaMailSender;
    @Override
    public Mono<SuccessResponse> sendEmail(String email, String type) {
        return Mono.create(sink -> {
            String checkCode = CharUtil.getCharAndNumber(6).toUpperCase();
            this.setRedisMono(email,type,checkCode,sink);
        });
    }

    private void setRedisMono(String email, String type, String checkCode, MonoSink<SuccessResponse> sink) {
        Mono<Boolean> redisMono = RedisUtil.setString(email + ":" + type, checkCode, 600);
        redisMono.subscribe(bool -> {
            System.out.println("redis结果" + bool.toString());
            try {
                this.sendEmailMono(bool, email, checkCode, sink);
            } catch (CustomizeException e) {
                sink.error(new CustomizeException("redis写入错误"));
            }
        });
    }

    private void sendEmailMono(Boolean bool, String email, String checkCode, MonoSink<SuccessResponse> sink) throws CustomizeException {
        if (!bool) {
            throw new CustomizeException("redis不可用");
        }
        Mono<Boolean> emailMono =  Mono.create(emailSink -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom("fzcode@126.com", "fzcode");
                helper.setTo(email);
                helper.setSubject("您好");
                helper.setText("<div>\n" +
                        "    <div style=\"\n" +
                        "    color: #ffffff;\n" +
                        "    background: #000000;\n" +
                        "    width: 148px;\n" +
                        "    padding: 12px;\n" +
                        "    text-align: center;\n" +
                        "    \"><h2>\n" +
                        "    " + checkCode + "\n" +
                        "    </h2></div>\n" +
                        "    <h3>十分钟内有效</h3>\n" +
                        "</div>", true);
                javaMailSender.send(message);
            } catch (Exception e) {
                System.out.println("邮件发送失败:" + e.getMessage());
                emailSink.error(new CustomizeException("邮件发送失败"));
                return;
            }
            emailSink.success(true);
        });
        emailMono.subscribe(s->{
            if(s){
                sink.success(new SuccessResponse("发送成功",""));
            }
        });
    }
}
