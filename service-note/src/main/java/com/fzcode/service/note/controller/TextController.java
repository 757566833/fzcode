package com.fzcode.service.note.controller;

import com.fzcode.service.note.dto.request.Text.TextReqCreateDTO;
import com.fzcode.service.note.dto.request.Text.TextReqPatchDTO;
import com.fzcode.service.note.dto.request.Text.TextReqUpdateDTO;
import com.fzcode.service.note.dto.response.IndexTextListResDTO;
import com.fzcode.service.note.dto.response.ListResDTO;
import com.fzcode.service.note.dto.response.SuccessResDTO;
import com.fzcode.service.note.exception.CustomizeException;
import com.fzcode.service.note.flow.TextFlow;
import com.fzcode.service.note.repositroy.dbInterface.text.TextDBGetById;
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
    public SuccessResDTO getList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        ListResDTO<IndexTextListResDTO> pageList = textFlow.findAll(page, size);
        return new SuccessResDTO("查询成功", pageList);
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
        TextDBGetById textResDTO = textFlow.findById(id);
        return new SuccessResDTO("查询", textResDTO);
    }
}
