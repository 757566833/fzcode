package com.fzcode.apiblog.controller;

import com.fzcode.apiblog.config.Services;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryCreateRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextCreateRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextGetListRequest;
import com.fzcode.internalcommon.dto.servicenote.response.category.CategoryResponse;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.internalcommon.utils.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Api(tags = "文章模块")
@RestController
@RequestMapping(value = "/note")
public class NoteController {
    Services services;
    @Autowired
    public void setServices(Services services){
        this.services = services;
    }

    @ApiOperation(value = "获取文章分类列表")
    @GetMapping(value = "/category/list")
    public SuccessResponse getCategoryList() {
        WebClient client = WebClient.create(services.getService().getNote().getHost());
        List<CategoryResponse> listResponseDTO =  client.get()
                .uri("/category/list")
                .exchange()
                .block()
                .bodyToMono(List.class)
//                .bodyToMono(new TypeReference<ListResponseDTO<TextResponse>>(){}.getType())
                .block();
        return new SuccessResponse("查询成功", listResponseDTO);
    }

    @ApiOperation(value = "添加文章分类")
    @PostMapping(value = "/category/add")
    public SuccessResponse addCategory(@RequestBody @Validated CategoryCreateRequest cateGoryCreateRequest,@RequestHeader("aid") String aid) {
        System.out.println("asd");
        WebClient client = WebClient.create(services.getService().getNote().getHost());
        Integer id =  client.post()
                .uri("/category/add")
                .bodyValue(cateGoryCreateRequest)
                .header("aid",aid)
                .exchange()
                .block()
                .bodyToMono(Integer.class)
//                .bodyToMono(new TypeReference<ListResponseDTO<TextResponse>>(){}.getType())
                .block();
        return new SuccessResponse("创建成功", id);
    }
    @ApiOperation(value = "添加文章")
    @PostMapping(value = "/text/create")
    public SuccessResponse createText(@RequestBody @Validated TextCreateRequest textCreateRequest,@RequestHeader("aid") String aid) {
        WebClient client = WebClient.create(services.getService().getNote().getHost());
        Map listResponseDTO =  client.post()
                .uri("/text/create")
                .bodyValue(textCreateRequest)
                .header("aid",aid)
                .exchange()
                .block()
                .bodyToMono(Map.class)
//                .bodyToMono(new TypeReference<ListResponseDTO<TextResponse>>(){}.getType())
                .block();
        return new SuccessResponse("添加成功", listResponseDTO);
    }
    @ApiOperation(value = "获取文章列表")
    @GetMapping(value = "/text/list")
    public SuccessResponse getTextList(TextGetListRequest textGetListRequest) {
        MultiValueMap<String, String>  params = BeanUtils.bean2MultiValueMap(textGetListRequest);
        WebClient client = WebClient.create(services.getService().getNote().getHost());
        MultiValueMap<String, String> finalParams = params;
        ListResponseDTO<TextResponse> listResponseDTO =  client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/text/list")
                        .queryParams(finalParams)
                        .build())
                .exchange()
                .block()
                .bodyToMono(ListResponseDTO.class)
//                .bodyToMono(new TypeReference<ListResponseDTO<TextResponse>>(){}.getType())
                .block();
        return new SuccessResponse("查询成功", listResponseDTO);
    }
    @ApiOperation(value = "获取指定文章")
    @GetMapping(value = "/text/{id}")
    public SuccessResponse getTextList(@PathVariable(name = "id") String id) {
        WebClient client = WebClient.create(services.getService().getNote().getHost());
        Map map =  client.get()
                .uri("/text/get/"+id)
                .exchange()
                .block()
                .bodyToMono(Map.class)
//                .bodyToMono(new TypeReference<ListResponseDTO<TextResponse>>(){}.getType())
                .block();
        return new SuccessResponse("查询成功", map);
    }
    @ApiOperation(value = "创建文章分类")
    @PostMapping(value = "/category/create")
    public SuccessResponse createCategory(@RequestBody @Validated CategoryCreateRequest categoryCreateRequest, @RequestHeader("aid") String aid,@RequestHeader("authority") String authority) {
        WebClient client = WebClient.create(services.getService().getNote().getHost());
        String listResponseDTO =  client.post()
                .uri("category/create")
                .bodyValue(categoryCreateRequest)
                .header("aid",aid)
                .header("authority",authority)
                .exchange()
                .block()
                .bodyToMono(String.class)
//                .bodyToMono(new TypeReference<ListResponseDTO<TextResponse>>(){}.getType())
                .block();
        return new SuccessResponse("查询成功", listResponseDTO);
    }
}
