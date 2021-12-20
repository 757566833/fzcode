package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryRequest;
import com.fzcode.internalcommon.dto.servicenote.request.note.SearchRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.SelfListRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextListRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextRequest;
import com.fzcode.internalcommon.dto.servicenote.response.category.CategoryResponse;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.http.Http;
import com.fzcode.internalcommon.utils.JSONUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "文章模块(需要登陆后查询)")
@RestController
@RequestMapping(value = "/text")
public class TextController {
    Http http;
    Services services;
    @Autowired
    public void setHttp( Http http){
        this.http = http;
    }
    @Autowired
    public void setServices(Services services){
        this.services = services;
    }

    @ApiOperation(value = "获取文章列表")
    @GetMapping(value = "/self/list")
    public SuccessResponse getSelfList(SelfListRequest selfListRequest, @RequestHeader("aid") String aid) throws CustomizeException {
        System.out.println("getSelfList");
        HttpHeaders headers = new HttpHeaders();
        headers.add("aid",aid);
        ListResponseDTO<TextResponse> listResponseDTO  =  http.get(services.getService().getNote().getHost()+"/text/self/list", selfListRequest,headers,ListResponseDTO.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }
}
