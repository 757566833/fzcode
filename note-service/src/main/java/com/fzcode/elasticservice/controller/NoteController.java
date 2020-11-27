package com.fzcode.elasticservice.controller;

import com.fzcode.elasticservice.dto.request.*;
import com.fzcode.elasticservice.dto.response.NoteResDTO;
import com.fzcode.elasticservice.dto.response.SuccessResDTO;
import com.fzcode.elasticservice.entity.Notes;
import com.fzcode.elasticservice.exception.CustomizeException;
import com.fzcode.elasticservice.flow.NoteFlow;
import com.fzcode.elasticservice.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/elastic/note")
public class NoteController {

    NoteFlow noteFlow;

    @Autowired
    public void setNoteFlow(NoteFlow noteFlow) {
        this.noteFlow = noteFlow;
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
        Page<Notes> pageList = noteFlow.findAll(currentPage, currentSize);
        return new SuccessResDTO("查询成功", BeanUtils.pageList2ResList(pageList));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResDTO create(@RequestBody @Validated NoteReqCreateDTO noteReqCreateDTO) throws CustomizeException {
        String nid = noteFlow.create(noteReqCreateDTO);
        Map map = new HashMap();
        map.put("nid", nid);
        return new SuccessResDTO("存储成功", map);
    }


    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResDTO update(@RequestBody @Validated NoteReqUpdateDTO noteReqUpdateDTO, @PathVariable(name = "id") Integer id) throws CustomizeException {
        String nid = noteFlow.update(id, noteReqUpdateDTO);
        Map map = new HashMap();
        map.put("nid", nid);
        return new SuccessResDTO("更新成功", map);
    }

    @PatchMapping(value = "/patch/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResDTO patch(@RequestBody @Validated NoteReqPatchDTO noteReqPatchDTO, @PathVariable(name = "id") Integer id) throws CustomizeException {
        String nid = noteFlow.patch(id, noteReqPatchDTO);
        Map map = new HashMap();
        map.put("nid", nid);
        return new SuccessResDTO("更新成功", map);
    }

    @DeleteMapping(value = "/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResDTO delete(@PathVariable(name = "id") Integer id) throws CustomizeException {
        String nid = noteFlow.delete(id);
        Map map = new HashMap();
        map.put("nid", nid);
        return new SuccessResDTO("更新成功", map);
    }

    @GetMapping(value = "/get/{id}")
    public SuccessResDTO getById(@PathVariable(name = "id") Integer id) throws CustomizeException {
        NoteResDTO noteResDTO = noteFlow.findById(id);
        return new SuccessResDTO("查询", noteResDTO);
    }
}
