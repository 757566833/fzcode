package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.internalcommon.dto.common.ListDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicenote.request.note.SearchRequest;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.http.Http;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "es文章模块")
@RestController
@RequestMapping(value = "/note")
public class NoteController {
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

    @ApiOperation(value = "搜索")
    @GetMapping(value = "/search")
    public SuccessResponse searchNote(SearchRequest searchRequest) throws CustomizeException {
        ListDTO listDTO =  http.get(services.getService().getNote().getHost()+"/note/search",searchRequest, ListDTO.class);
        return new SuccessResponse("查询成功", listDTO);
    }
}
