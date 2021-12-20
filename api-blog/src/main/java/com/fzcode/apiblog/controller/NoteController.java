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
        List<CategoryResponse> listResponseDTO =  http.get(services.getService().getNote().getHost()+"/category/list", List.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }
    @ApiOperation(value = "添加文章")
    @PostMapping(value = "/text/create")
    public SuccessResponse createText(@RequestBody @Validated TextRequest textRequest, @RequestHeader("aid") String aid) throws CustomizeException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("aid",aid);
        HttpEntity<TextRequest> requestParam = new HttpEntity<TextRequest>(textRequest, headers);
        Map listResponseDTO  =   http.post(services.getService().getNote().getHost()+"/text/create",requestParam, Map.class);
        return new SuccessResponse("添加成功", listResponseDTO);
    }
    @ApiOperation(value = "获取文章列表")
    @GetMapping(value = "/text/list")
    public SuccessResponse getTextList(TextListRequest textListRequest) throws CustomizeException {
        ListResponseDTO<TextResponse> listResponseDTO  =  http.get(services.getService().getNote().getHost()+"/text/list", textListRequest,ListResponseDTO.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }
    @ApiOperation(value = "获取文章列表")
    @GetMapping(value = "/text/self/list")
    public SuccessResponse getSelfList(SelfListRequest selfListRequest, @RequestHeader HttpHeaders httpHeaders) throws CustomizeException {
        System.out.println("getSelfList");
        System.out.println(JSONUtils.stringify(httpHeaders));
        System.out.println(httpHeaders.get("aid"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("aid",httpHeaders.get("aid").toString());
        System.out.println(httpHeaders.get("aid"));
        ListResponseDTO<TextResponse> listResponseDTO  =  http.get(services.getService().getNote().getHost()+"/text/self/list", selfListRequest,headers,ListResponseDTO.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }
    @ApiOperation(value = "获取指定文章")
    @GetMapping(value = "/text/{id}")
    public SuccessResponse getTextList(@PathVariable(name = "id") String id) throws CustomizeException {
        Map map  =   http.get(services.getService().getNote().getHost()+"/text/get/"+id, Map.class);
        return new SuccessResponse("查询成功", map);
    }
    @ApiOperation(value = "创建文章分类")
    @PostMapping(value = "/category/create")
    public SuccessResponse createCategory(@RequestBody @Validated CategoryRequest categoryRequest, @RequestHeader("aid") String aid, @RequestHeader("authority") String authority) throws CustomizeException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("aid",aid);
        headers.add("authority",authority);
        HttpEntity<CategoryRequest> requestParam = new HttpEntity<CategoryRequest>(categoryRequest, headers);
        String listResponseDTO  =  http.post(services.getService().getNote().getHost()+"/category/create",requestParam, String.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }

    @ApiOperation(value = "创建文章分类")
    @GetMapping(value = "/search")
    public SuccessResponse searchNote(SearchRequest searchRequest) throws CustomizeException {
        ListResponseDTO listResponseDTO  =  http.get(services.getService().getNote().getHost()+"/note/search",searchRequest, ListResponseDTO.class);
        return new SuccessResponse("查询成功", listResponseDTO);
    }
}
