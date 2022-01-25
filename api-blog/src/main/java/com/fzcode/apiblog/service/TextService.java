package com.fzcode.apiblog.service;

import com.fzcode.internalcommon.dto.apiblog.response.TextDetailDTO;
import com.fzcode.internalcommon.exception.CustomizeException;

public interface TextService {
    public TextDetailDTO getNote(String id) throws CustomizeException;
}
