package com.fzcode.noteservice.controller;

import com.fzcode.noteservice.dto.request.Column.ColumnReqCreateDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqDeleteDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqPatchDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqUpdateDTO;
import com.fzcode.noteservice.dto.response.SuccessResDTO;
import com.fzcode.noteservice.entity.Categorys;
import com.fzcode.noteservice.exception.CustomizeException;
import com.fzcode.noteservice.flow.CategoryFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/column")
public class CategoryController {
    CategoryFlow categoryFlow;

    @Autowired
    public void setColumnFlow(CategoryFlow categoryFlow) {
        this.categoryFlow = categoryFlow;
    }

    @GetMapping(value = "/list")
    public SuccessResDTO getList() {
        List<Categorys> categorysList = categoryFlow.findAll();
        return new SuccessResDTO("查询成功", categorysList);
    }

    @GetMapping(value = "/id/{id}")
    public SuccessResDTO getById(@PathVariable(name = "id") Integer id) throws CustomizeException {
        Categorys categorys = categoryFlow.findById(id);
        return new SuccessResDTO("查询成功", categorys);
    }

    @PostMapping(value = "/add")
    public SuccessResDTO add(@RequestBody @Validated ColumnReqCreateDTO columnReqCreateDTO, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryFlow.create(columnReqCreateDTO, aid);
        return new SuccessResDTO("创建成功", cid);
    }

    @PutMapping(value = "/update")
    public SuccessResDTO update(@RequestBody @Validated ColumnReqUpdateDTO columnReqUpdateDTO, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryFlow.update(columnReqUpdateDTO,aid);
        return new SuccessResDTO("更新成功", cid);
    }

    @PatchMapping(value = "/patch")
    public SuccessResDTO patch(@RequestBody @Validated ColumnReqPatchDTO columnReqPatchDTO, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryFlow.patch(columnReqPatchDTO,aid);
        return new SuccessResDTO("更新成功", cid);
    }

    @DeleteMapping(value = "/delete")
    public SuccessResDTO del(@RequestBody @Validated ColumnReqDeleteDTO columnReqDeleteDTO, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryFlow.delete(columnReqDeleteDTO,aid);
        return new SuccessResDTO("删除", cid);
    }
}
