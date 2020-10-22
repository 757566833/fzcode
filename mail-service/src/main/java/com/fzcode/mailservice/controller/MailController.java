package com.fzcode.mailservice.controller;

import com.fzcode.mailservice.http.ResponseDTO;
import com.fzcode.mailservice.util.CharUtil;
import com.fzcode.mailservice.util.MailUtil;
import com.fzcode.mailservice.util.RedisUtil;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/mail")
public class MailController {
//    @Autowired
//    private ReactiveRedisTemplate<String,String> reactiveRedisTemplate;

    @PostMapping(value = "/sendEmail",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseDTO> sendEmail(@RequestBody @Validated MailDTO mailDTO){
        System.out.println("sendMail"+mailDTO.getEmail());
        return  Mono.create(sink->{

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode("200");
            responseDTO.setData("yes");
            responseDTO.setMessage("send success");
            String checkCode = CharUtil.getCharAndNumber(6).toUpperCase();
//            sink.success(responseDTO);
            RedisUtil.setString(mailDTO.getEmail(),checkCode,600).subscribe(result->{
                if(result){
                    MailUtil.sendMail(mailDTO.getEmail(),checkCode).subscribe(sendEmailResult->{
                        System.out.println("MailUtil,sendMail:"+sendEmailResult);
                        if (!sendEmailResult) {
                            responseDTO.setData("error");
                            responseDTO.setCode("500");
                            responseDTO.setMessage("send fail");
                        }
                        sink.success(responseDTO);
                    });
                }else{
                    responseDTO.setData("error");
                    responseDTO.setCode("500");
                    responseDTO.setMessage("send fail");
                    sink.success(responseDTO);
                }
            });
        });
    }
}
