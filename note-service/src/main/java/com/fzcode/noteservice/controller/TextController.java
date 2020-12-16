package com.fzcode.noteservice.controller;

import com.fzcode.noteservice.dto.request.Text.TextReqCreateDTO;
import com.fzcode.noteservice.dto.request.Text.TextReqPatchDTO;
import com.fzcode.noteservice.dto.request.Text.TextReqUpdateDTO;
import com.fzcode.noteservice.dto.response.TextGetResDTO;
import com.fzcode.noteservice.dto.response.TextResDTO;
import com.fzcode.noteservice.dto.response.SuccessResDTO;
import com.fzcode.noteservice.entity.Texts;
import com.fzcode.noteservice.exception.CustomizeException;
import com.fzcode.noteservice.flow.TextFlow;
import com.fzcode.noteservice.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/text")
public class TextController {

    TextFlow textFlow;

    @Autowired
    public void setNoteFlow(TextFlow textFlow) {
        this.textFlow = textFlow;
    }

    @GetMapping(value = "/list2")
    public SuccessResDTO getList2() {
        return new SuccessResDTO("查询成功", "page");
    }

    @GetMapping(value = "/list")
    public SuccessResDTO getList(@RequestParam(value = "page") Integer page, @RequestParam(value = "size") Integer size) {
        Integer currentPage;
        if (page == null) {
            currentPage = 1;
        } else {
            currentPage = page;
        }
        Integer currentSize;
        if (page == null) {
            currentSize = 10;
        } else {
            currentSize = size;
        }
        Page<Texts> pageList = textFlow.findAll(currentPage, currentSize);
        return new SuccessResDTO("查询成功", ListUtils.pageList2ResList(pageList));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResDTO create(@RequestBody @Validated TextReqCreateDTO textReqCreateDTO, @RequestHeader("aid") String aid) throws CustomizeException {
        if (aid == null) {
            throw new CustomizeException("用户未登录");
        }
        String tid = textFlow.create(textReqCreateDTO,Integer.valueOf(aid));
        Map map = new HashMap();
        map.put("tid", tid);
        return new SuccessResDTO("存储成功", map);
    }


    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResDTO update(@RequestBody @Validated TextReqUpdateDTO textReqUpdateDTO, @PathVariable(name = "id") Integer id) throws CustomizeException {
        String tid = textFlow.update(id, textReqUpdateDTO);
        Map map = new HashMap();
        map.put("tid", tid);
        return new SuccessResDTO("更新成功", map);
    }

    @PatchMapping(value = "/patch/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResDTO patch(@RequestBody @Validated TextReqPatchDTO textReqPatchDTO, @PathVariable(name = "id") Integer id) throws CustomizeException {
        String tid = textFlow.patch(id, textReqPatchDTO);
        Map map = new HashMap();
        map.put("tid", tid);
        return new SuccessResDTO("更新成功", map);
    }

    @DeleteMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResDTO delete(@PathVariable(name = "id") Integer id) throws CustomizeException {
        String tid = textFlow.delete(id);
        Map map = new HashMap();
        map.put("tid", tid);
        return new SuccessResDTO("更新成功", map);
    }

    @GetMapping(value = "/get/{id}")
    public SuccessResDTO getById(@PathVariable(name = "id") Integer id) throws CustomizeException {
        TextGetResDTO textResDTO = textFlow.findById(id);
        return new SuccessResDTO("查询", textResDTO);
    }
}
