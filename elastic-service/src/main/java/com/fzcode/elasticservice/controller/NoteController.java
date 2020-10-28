package com.fzcode.elasticservice.controller;

import com.fzcode.elasticservice.dto.request.*;
import com.fzcode.elasticservice.dto.response.NoteResDTO;
import com.fzcode.elasticservice.dto.response.SuccessResDTO;
import com.fzcode.elasticservice.entity.Notes;
import com.fzcode.elasticservice.exception.CustomizeException;
import com.fzcode.elasticservice.flow.NoteFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/elastic/note", consumes = MediaType.APPLICATION_JSON_VALUE)
public class NoteController {

    NoteFlow noteFlow;

    @Autowired
    public void setNoteFlow(NoteFlow noteFlow) {
        this.noteFlow = noteFlow;
    }

    @GetMapping(value = "/list")
    public SuccessResDTO getList(@RequestBody @Validated NoteReqGetListDTO noteReqGetListDTO) {
        Page<Notes> page = noteFlow.findAll(noteReqGetListDTO);
        return new SuccessResDTO("查询成功", page);
    }

    @PostMapping(value = "/create")
    public SuccessResDTO create(@RequestBody @Validated NoteReqCreateDTO noteReqCreateDTO) throws CustomizeException {
        String nid = noteFlow.create(noteReqCreateDTO);
        Map map = new HashMap();
        map.put("nid", nid);
        return new SuccessResDTO("存储成功", map);
    }


    @PutMapping(value = "/update")
    public SuccessResDTO update(@RequestBody @Validated NoteReqUpdateDTO noteReqUpdateDTO) throws CustomizeException {
        String nid = noteFlow.update(noteReqUpdateDTO);
        Map map = new HashMap();
        map.put("nid", nid);
        return new SuccessResDTO("更新成功", map);
    }

    @PatchMapping(value = "/patch")
    public SuccessResDTO patch(@RequestBody @Validated NoteReqPatchDTO noteReqPatchDTO) throws CustomizeException {
        String nid = noteFlow.patch(noteReqPatchDTO);
        Map map = new HashMap();
        map.put("nid", nid);
        return new SuccessResDTO("更新成功", map);
    }

    @DeleteMapping(value = "/delete")
    public SuccessResDTO delete(@RequestBody @Validated NoteReqDeleteDTO noteReqDeleteDTO) throws CustomizeException {
        String nid = noteFlow.delete(noteReqDeleteDTO);
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
