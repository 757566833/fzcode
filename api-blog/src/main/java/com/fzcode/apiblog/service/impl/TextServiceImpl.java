package com.fzcode.apiblog.service.impl;

import com.fzcode.apiblog.config.Services;
import com.fzcode.apiblog.service.TextService;
import com.fzcode.internalcommon.dto.apiblog.response.TextDetailDTO;
import com.fzcode.internalcommon.dto.serviceauth.common.Users;
import com.fzcode.internalcommon.dto.servicenote.common.db.Texts;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.http.Http;
import com.fzcode.internalcommon.utils.JSONUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextServiceImpl implements TextService {
    Http http;
    @Autowired
    public void setHttp( Http http){
        this.http = http;
    }

    Services services;
    @Autowired
    public void setServices(Services services){
        this.services = services;
    }

    @Override
    public TextDetailDTO getNote(String id) throws CustomizeException {
        System.out.println("getNote");
        Texts texts = http.get(services.getService().getNote().getHost()+"/text/get/"+id,Texts.class);
        System.out.println(JSONUtils.stringify(texts));
        String uid = texts.getCreateBy();
        System.out.println(uid);
        Users users = http.get(services.getService().getAuth().getHost()+"/user/get/"+uid, Users.class);
        System.out.println(JSONUtils.stringify(users));
        TextDetailDTO textDetailDTO = new TextDetailDTO();
        BeanUtils.copyProperties(texts,textDetailDTO);
        BeanUtils.copyProperties(users,textDetailDTO);
        System.out.println(JSONUtils.stringify(textDetailDTO));
        return textDetailDTO;
    }
}
