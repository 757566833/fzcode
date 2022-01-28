package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.internalcommon.crud.Create;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryRequest;
import com.fzcode.internalcommon.dto.servicenote.response.category.CategoryResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.http.Http;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "文章模块")
@RestController
@RequestMapping(value = "/category")
public class CategoryController {
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

    @ApiOperation(value = "获取文章分类列表")
    @GetMapping(value = "/list")
    public SuccessResponse getCategoryList() throws CustomizeException {
        List<CategoryResponse> listResponseDTO =  http.get(services.getService().getNote().getHost()+"/category/list", List.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }
    @ApiOperation(value = "创建文章分类")
    @PostMapping(value = "/create")
    public SuccessResponse createCategory(@RequestBody @Validated(value = Create.class) CategoryRequest categoryRequest, @RequestHeader("aid") String aid,@RequestHeader("uid") String uid, @RequestHeader("authority") String authority) throws CustomizeException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("aid",aid);
        headers.add("authority",authority);
        headers.add("uid",uid);
        HttpEntity<CategoryRequest> requestParam = new HttpEntity<CategoryRequest>(categoryRequest, headers);
        String listResponseDTO  =  http.post(services.getService().getNote().getHost()+"/category/create",requestParam, String.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }
}
