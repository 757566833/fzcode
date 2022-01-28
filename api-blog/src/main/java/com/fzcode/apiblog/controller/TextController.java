package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.apiblog.service.TextService;
import com.fzcode.internalcommon.crud.Create;
import com.fzcode.internalcommon.dto.apiblog.response.TextDetailDTO;
import com.fzcode.internalcommon.dto.common.ListDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicenote.request.text.SelfListRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextListRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextRequest;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.http.Http;
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

import java.util.Map;

@Api(tags = "文章模块")
@RestController
@RequestMapping(value = "/text")
public class TextController {
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

    TextService textService;
    @Autowired
    public void setTextService(TextService textService){
        this.textService = textService;
    }

    @ApiOperation(value = "添加文章")
    @PostMapping(value = "/create")
    public SuccessResponse createText(@RequestBody @Validated(value = Create.class) TextRequest textRequest, @RequestHeader("uid") String uid) throws CustomizeException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("uid",uid);
        HttpEntity<TextRequest> requestParam = new HttpEntity<TextRequest>(textRequest, headers);
        Map listResponseDTO  =   http.post(services.getService().getNote().getHost()+"/text/create",requestParam, Map.class);
        return new SuccessResponse("添加成功", listResponseDTO);
    }
    @ApiOperation(value = "获取文章列表")
    @GetMapping(value = "/list")
    public SuccessResponse getTextList(TextListRequest textListRequest) throws CustomizeException {
        ListDTO<TextResponse> listDTO =  http.get(services.getService().getNote().getHost()+"/text/list", textListRequest, ListDTO.class);
        return new SuccessResponse("查询成功", listDTO);
    }
    @ApiOperation(value = "获取自己的文章列表")
    @GetMapping(value = "/self/list")
    public SuccessResponse getSelfList(SelfListRequest selfListRequest, @RequestHeader("email") String email,@RequestHeader("uid") String uid, @RequestHeader("aid") String aid) throws CustomizeException {
        System.out.println("getSelfList");
        System.out.println(uid);
        System.out.println(aid);
        System.out.println(email);
        HttpHeaders headers = new HttpHeaders();
        headers.add("aid",aid);
        headers.add("uid",uid);
        headers.add("email",email);
        ListDTO<TextResponse> listDTO =  http.get(services.getService().getNote().getHost()+"/text/self/list", selfListRequest,headers, ListDTO.class);
        return new SuccessResponse("查询成功", listDTO);
    }
    @ApiOperation(value = "获取指定文章")
    @GetMapping(value = "/get/{id}")
    public SuccessResponse getTextList(@PathVariable(name = "id") String id) throws CustomizeException {
        System.out.println("获取指定文章"+id);
        TextDetailDTO textDetail  =  textService.getNote(id);
        return new SuccessResponse("查询成功", textDetail);
    }
}
