package com.fzcode.noteservice.controller;

import com.fzcode.noteservice.dto.request.Category.CateGoryReqCreateDTO;
import com.fzcode.noteservice.dto.request.Category.CateGoryReqDeleteDTO;
import com.fzcode.noteservice.dto.request.Category.CateGoryReqPatchDTO;
import com.fzcode.noteservice.dto.request.Category.CateGoryReqUpdateDTO;
import com.fzcode.noteservice.dto.response.SuccessResDTO;
import com.fzcode.noteservice.entity.Categories;
import com.fzcode.noteservice.exception.CustomizeException;
import com.fzcode.noteservice.flow.CategoryFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    CategoryFlow categoryFlow;

    @Autowired
    public void setColumnFlow(CategoryFlow categoryFlow) {
        this.categoryFlow = categoryFlow;
    }

    @GetMapping(value = "/list")
    public SuccessResDTO getList() {
        List<Categories> categoriesList = categoryFlow.findAll();
        return new SuccessResDTO("查询成功", categoriesList);
    }

    @GetMapping(value = "/id/{id}")
    public SuccessResDTO getById(@PathVariable(name = "id") Integer id) throws CustomizeException {
        Categories categories = categoryFlow.findById(id);
        return new SuccessResDTO("查询成功", categories);
    }

    @PostMapping(value = "/add")
    public SuccessResDTO add(@RequestBody @Validated CateGoryReqCreateDTO cateGoryReqCreateDTO, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryFlow.create(cateGoryReqCreateDTO, aid);
        return new SuccessResDTO("创建成功", cid);
    }

    @PutMapping(value = "/update")
    public SuccessResDTO update(@RequestBody @Validated CateGoryReqUpdateDTO cateGoryReqUpdateDTO, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryFlow.update(cateGoryReqUpdateDTO,aid);
        return new SuccessResDTO("更新成功", cid);
    }

    @PatchMapping(value = "/patch")
    public SuccessResDTO patch(@RequestBody @Validated CateGoryReqPatchDTO cateGoryReqPatchDTO, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryFlow.patch(cateGoryReqPatchDTO,aid);
        return new SuccessResDTO("更新成功", cid);
    }

    @DeleteMapping(value = "/delete")
    public SuccessResDTO del(@RequestBody @Validated CateGoryReqDeleteDTO cateGoryReqDeleteDTO, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryFlow.delete(cateGoryReqDeleteDTO,aid);
        return new SuccessResDTO("删除", cid);
    }
}
