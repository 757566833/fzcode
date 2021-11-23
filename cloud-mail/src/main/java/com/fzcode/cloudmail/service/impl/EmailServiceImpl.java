package com.fzcode.cloudmail.service.impl;

import com.fzcode.cloudmail.util.RedisUtil;
import com.fzcode.cloudmail.service.EmailService;
import com.fzcode.cloudmail.util.CharUtil;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.http.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    private JavaMailSender javaMailSender;
    @Autowired
    public void setJavaMailSender( JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    @Override
    public String sendEmail(String email, String type) throws CustomizeException {
        String checkCode = CharUtil.getCharAndNumber(6).toUpperCase();
        try {
            RedisUtil.setString(email + ":" + type, checkCode, 600);
        }catch (Exception e){
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"redis不可用");
        }
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
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"邮件发送失败");
        }
        return "success";
    }

}
