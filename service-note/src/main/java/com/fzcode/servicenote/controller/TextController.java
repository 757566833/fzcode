package com.fzcode.servicenote.controller;

import com.fzcode.internalcommon.crud.Create;
import com.fzcode.internalcommon.crud.Delete;
import com.fzcode.internalcommon.crud.FullUpdate;
import com.fzcode.internalcommon.crud.IncrementalUpdate;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;

import com.fzcode.internalcommon.dto.servicenote.request.text.TextListRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextRequest;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.servicenote.service.TextService;
import com.fzcode.servicenote.repositroy.mapper.TextDBGetByIdMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "文章模块")
@RestController
@RequestMapping(value = "/text")
public class TextController {

    TextService textService;

    @Autowired
    public void setTextService(TextService textService) {
        this.textService = textService;
    }

    @ApiOperation(value = "获取文章列表")
    @GetMapping(value = "/list")
    public ListResponseDTO<TextResponse> getList(TextListRequest textListRequest) {
        return textService.findAll(textListRequest.getPage(), textListRequest.getPageSize());
    }

    @ApiOperation(value = "创建文章")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Map create(@RequestBody @Validated(value = Create.class) TextRequest textRequest, @RequestHeader("aid") String aid) throws CustomizeException {
        if (aid == null) {
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"用户未登录");
        }
        Integer tid = textService.create(textRequest,Integer.valueOf(aid));
        Map map = new HashMap();
        map.put("tid", tid);
        return  map;
    }

    @ApiOperation(value = "修改文章-全量")
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse update(@RequestBody @Validated(value = FullUpdate.class)  TextRequest textRequest, @PathVariable(name = "id") Integer id) throws CustomizeException {
        String tid = textService.update(id, textRequest);
        Map map = new HashMap();
        map.put("tid", tid);
        return new SuccessResponse("更新成功", map);
    }

    @ApiOperation(value = "修改文章-增量")
    @PatchMapping(value = "/patch/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse patch(@RequestBody @Validated(value = IncrementalUpdate.class)  TextRequest textRequest, @PathVariable(name = "id") Integer id) throws CustomizeException {
        String tid = textService.patch(id, textRequest);
        Map map = new HashMap();
        map.put("tid", tid);
        return new SuccessResponse("更新成功", map);
    }

    @ApiOperation(value = "删除文章")
    @DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse delete(@RequestBody @Validated(value = Delete.class)  TextRequest textRequest) throws CustomizeException {
        Integer tid = textService.delete(textRequest.getTid());
        Map map = new HashMap();
        map.put("tid", tid);
        return new SuccessResponse("更新成功", map);
    }

    @ApiOperation(value = "获取文章")
    @GetMapping(value = "/get/{id}")
    public TextDBGetByIdMapper getById(@PathVariable(name = "id") Integer id) throws CustomizeException {
        return textService.findById(id);
    }
}
