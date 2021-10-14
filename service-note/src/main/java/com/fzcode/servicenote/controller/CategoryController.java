package com.fzcode.servicenote.controller;

import com.fzcode.internalcommon.dto.http.SuccessResponse;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryCreateRequest;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryDeleteRequest;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryPatchRequest;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryUpdateRequest;
import com.fzcode.servicenote.entity.Categories;
import com.fzcode.servicenote.exception.CustomizeException;
import com.fzcode.servicenote.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "分类模块")
@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public void setColumnFlow(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "获取分类列表")
    @GetMapping(value = "/list")
    public List<Categories> getList() {
        return categoryService.findAll();
    }

    @ApiOperation(value = "获取分类")
    @GetMapping(value = "/id/{id}")
    public SuccessResponse getById(@PathVariable(name = "id") Integer id) throws CustomizeException {
        Categories categories = categoryService.findById(id);
        return new SuccessResponse("查询成功", categories);
    }

    @ApiOperation(value = "添加分类")
    @PostMapping(value = "/add")
    public Integer add(@RequestBody @Validated CategoryCreateRequest cateGoryCreateRequest, @RequestHeader("aid") Integer aid,@RequestHeader("authority") String authority) throws CustomizeException {
        System.out.println("add");
        return categoryService.create(cateGoryCreateRequest, aid,authority);
    }

    @ApiOperation(value = "修改分类-全量")
    @PutMapping(value = "/update")
    public SuccessResponse update(@RequestBody @Validated CategoryUpdateRequest categoryUpdateRequest, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryService.update(categoryUpdateRequest,aid);
        return new SuccessResponse("更新成功", cid);
    }

    @ApiOperation(value = "修改分类-增量")
    @PatchMapping(value = "/patch")
    public SuccessResponse patch(@RequestBody @Validated CategoryPatchRequest categoryPatchRequest, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryService.patch(categoryPatchRequest,aid);
        return new SuccessResponse("更新成功", cid);
    }

    @ApiOperation(value = "删除分类")
    @DeleteMapping(value = "/delete")
    public SuccessResponse del(@RequestBody @Validated CategoryDeleteRequest categoryDeleteRequest, @RequestHeader("aid") Integer aid) throws CustomizeException {
        Integer cid = categoryService.delete(categoryDeleteRequest,aid);
        return new SuccessResponse("删除", cid);
    }
}
