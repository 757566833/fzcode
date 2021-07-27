package com.fzcode.servicenote.controller;

import com.alibaba.fastjson.JSON;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicenote.request.text.*;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.servicenote.exception.CustomizeException;
import com.fzcode.servicenote.service.TextService;
import com.fzcode.servicenote.repositroy.mapper.TextDBGetByIdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/text")
public class TextController {

    TextService textService;

    @Autowired
    public void setNoteFlow(TextService textService) {
        this.textService = textService;
    }

    @GetMapping(value = "/list2")
    public SuccessResponse getList2() {
        return new SuccessResponse("查询成功", "page");
    }

    @GetMapping(value = "/list")
    public ListResponseDTO<TextResponse> getList(TextGetListRequest textGetListRequest) {
        return textService.findAll(textGetListRequest.getPage(), textGetListRequest.getPageSize());
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Map create(@RequestBody @Validated TextCreateRequest textCreateRequest, @RequestHeader("aid") String aid) throws CustomizeException {
        if (aid == null) {
            throw new CustomizeException("用户未登录");
        }
        String tid = textService.create(textCreateRequest,Integer.valueOf(aid));
        Map map = new HashMap();
        map.put("tid", tid);
        return  map;
    }


    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse update(@RequestBody @Validated TextUpdateRequest textUpdateRequest, @PathVariable(name = "id") Integer id) throws CustomizeException {
        String tid = textService.update(id, textUpdateRequest);
        Map map = new HashMap();
        map.put("tid", tid);
        return new SuccessResponse("更新成功", map);
    }

    @PatchMapping(value = "/patch/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse patch(@RequestBody @Validated TextPatchRequest textPatchRequest, @PathVariable(name = "id") Integer id) throws CustomizeException {
        String tid = textService.patch(id, textPatchRequest);
        Map map = new HashMap();
        map.put("tid", tid);
        return new SuccessResponse("更新成功", map);
    }

    @DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse delete(@RequestBody @Validated TextDeleteRequest textDeleteRequest) throws CustomizeException {
        String tid = textService.delete(textDeleteRequest.getTid());
        Map map = new HashMap();
        map.put("tid", tid);
        return new SuccessResponse("更新成功", map);
    }

    @GetMapping(value = "/get/{id}")
    public TextDBGetByIdMapper getById(@PathVariable(name = "id") Integer id) throws CustomizeException {
        return textService.findById(id);
    }
}
