package com.fzcode.service.mail.util;

import com.fzcode.service.mail.exception.CustomizeException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import reactor.core.publisher.Mono;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    private static JavaMailSenderImpl javaMailSender;
    private RedisTemplate<String, String> redisTemplate;

    static {
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.126.com");//链接服务器
        //javaMailSender.setPort(25);//默认使用25端口发送
        // IMAP ： TKOMBJXTWWYJQTMS
        javaMailSender.setUsername("fzcode@126.com");//账号
        javaMailSender.setPassword("TKOMBJXTWWYJQTMS");//授权码
        javaMailSender.setDefaultEncoding("UTF-8");

        Properties properties = new Properties();
        //properties.setProperty("mail.debug", "true");//启用调试
        //properties.setProperty("mail.smtp.timeout", "1000");//设置链接超时
        //设置通过ssl协议使用465端口发送、使用默认端口（25）时下面三行不需要
        properties.setProperty("mail.smtp.auth", "true");//开启认证
        properties.setProperty("mail.smtp.socketFactory.port", "465");//设置ssl端口
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        javaMailSender.setJavaMailProperties(properties);
    }

    public static Mono<Boolean> sendMail(String email, String checkCode) throws CustomizeException {
        System.out.println("await sendMail");
        return Mono.create(sink -> {
            System.out.println("async");
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
                sink.error(new CustomizeException("邮件发送失败"));
                return;
            }
            sink.success(true);
        });

    }
}
