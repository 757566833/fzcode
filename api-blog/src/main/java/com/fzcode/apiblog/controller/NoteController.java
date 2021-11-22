package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.apiblog.http.Http;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryCreateRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextCreateRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextGetListRequest;
import com.fzcode.internalcommon.dto.servicenote.response.category.CategoryResponse;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "文章模块")
@RestController
@RequestMapping(value = "/note")
public class NoteController {
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
    @ApiOperation(value = "获取文章分类列表")
    @GetMapping(value = "/category/list")
    public SuccessResponse getCategoryList() throws CustomizeException {
        List<CategoryResponse> listResponseDTO =  http.post(services.getService().getNote().getHost()+"/category/list", List.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }

    @ApiOperation(value = "添加文章分类")
    @PostMapping(value = "/category/add")
    public SuccessResponse addCategory(@RequestBody @Validated CategoryCreateRequest cateGoryCreateRequest,@RequestHeader("aid") String aid) {
        Integer id =  http.post(services.getService().getNote().getHost()+"/category/add",cateGoryCreateRequest, Integer.class);
        return new SuccessResponse("创建成功", id);
    }
    @ApiOperation(value = "添加文章")
    @PostMapping(value = "/text/create")
    public SuccessResponse createText(@RequestBody @Validated TextCreateRequest textCreateRequest,@RequestHeader("aid") String aid) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("aid",aid);
        HttpEntity<TextCreateRequest> requestParam = new HttpEntity<TextCreateRequest>(textCreateRequest, headers);
        Map listResponseDTO  =   http.post(services.getService().getNote().getHost()+"/text/create",requestParam, Map.class);
        return new SuccessResponse("添加成功", listResponseDTO);
    }
    @ApiOperation(value = "获取文章列表")
    @GetMapping(value = "/text/list")
    public SuccessResponse getTextList(TextGetListRequest textGetListRequest) {
        ListResponseDTO<TextResponse> listResponseDTO  =  http.get(services.getService().getNote().getHost()+"/text/list", textGetListRequest,ListResponseDTO.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }
    @ApiOperation(value = "获取指定文章")
    @GetMapping(value = "/text/{id}")
    public SuccessResponse getTextList(@PathVariable(name = "id") String id) {
        Map map  =   http.get(services.getService().getNote().getHost()+"/text/get/"+id, Map.class);
        return new SuccessResponse("查询成功", map);
    }
    @ApiOperation(value = "创建文章分类")
    @PostMapping(value = "/category/create")
    public SuccessResponse createCategory(@RequestBody @Validated CategoryCreateRequest categoryCreateRequest, @RequestHeader("aid") String aid,@RequestHeader("authority") String authority) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("aid",aid);
        headers.add("authority",authority);
        HttpEntity<CategoryCreateRequest> requestParam = new HttpEntity<CategoryCreateRequest>(categoryCreateRequest, headers);
        String listResponseDTO  =  http.post(services.getService().getNote().getHost()+"/category/create",requestParam, String.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }
}
