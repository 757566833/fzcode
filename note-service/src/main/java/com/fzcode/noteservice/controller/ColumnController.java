package com.fzcode.noteservice.controller;

import com.fzcode.noteservice.dto.request.Column.ColumnReqCreateDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqDeleteDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqPatchDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqUpdateDTO;
import com.fzcode.noteservice.dto.response.SuccessResDTO;
import com.fzcode.noteservice.entity.Columns;
import com.fzcode.noteservice.exception.CustomizeException;
import com.fzcode.noteservice.flow.ColumnFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/column")
public class ColumnController {
    ColumnFlow columnFlow;

    @Autowired
    public void setColumnFlow(ColumnFlow columnFlow) {
        this.columnFlow = columnFlow;
    }

    @GetMapping(value = "/list")
    public SuccessResDTO getList() {
        List<Columns> columnsList = columnFlow.findAll();
        return new SuccessResDTO("查询成功", columnsList);
    }

    @GetMapping(value = "/id/{id}")
    public SuccessResDTO getById(@PathVariable(name = "id") Integer id) throws CustomizeException {
        Columns columns = columnFlow.findById(id);
        return new SuccessResDTO("查询成功", columns);
    }

    @PostMapping(value = "/add")
    public SuccessResDTO add(@RequestBody @Validated ColumnReqCreateDTO columnReqCreateDTO) throws CustomizeException {
        Integer cid = columnFlow.create(columnReqCreateDTO);
        return new SuccessResDTO("创建成功", cid);
    }

    @PutMapping(value = "/update")
    public SuccessResDTO update(@RequestBody @Validated ColumnReqUpdateDTO columnReqUpdateDTO) throws CustomizeException {
        Integer cid = columnFlow.update(columnReqUpdateDTO);
        return new SuccessResDTO("更新成功", cid);
    }

    @PatchMapping(value = "/patch")
    public SuccessResDTO patch(@RequestBody @Validated ColumnReqPatchDTO columnReqPatchDTO) throws CustomizeException {
        Integer cid = columnFlow.patch(columnReqPatchDTO);
        return new SuccessResDTO("更新成功", cid);
    }

    @DeleteMapping(value = "/delete")
    public SuccessResDTO del(@RequestBody @Validated ColumnReqDeleteDTO columnReqDeleteDTO) throws CustomizeException {
        Integer cid = columnFlow.delete(columnReqDeleteDTO);
        return new SuccessResDTO("删除", cid);
    }
}
