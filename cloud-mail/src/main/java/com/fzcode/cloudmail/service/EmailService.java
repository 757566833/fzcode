package com.fzcode.cloudmail.service;

import com.fzcode.internalcommon.exception.CustomizeException;
import org.springframework.stereotype.Service;


@Service
public interface EmailService {
    public String  sendEmail(String email, String type) throws CustomizeException;
}
