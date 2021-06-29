package com.fzcode.noteservice.flow;

import com.fzcode.noteservice.dto.request.Category.CateGoryReqCreateDTO;
import com.fzcode.noteservice.dto.request.Category.CateGoryReqDeleteDTO;
import com.fzcode.noteservice.dto.request.Category.CateGoryReqPatchDTO;
import com.fzcode.noteservice.dto.request.Category.CateGoryReqUpdateDTO;
import com.fzcode.noteservice.entity.Categories;
import com.fzcode.noteservice.exception.CustomizeException;
import com.fzcode.noteservice.service.DB.CategoryDBService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryFlow {
    CategoryDBService categoryDBService;

    @Autowired
    public void setColumnDBService(CategoryDBService categoryDBService) {
        this.categoryDBService = categoryDBService;
    }

    public Integer create(CateGoryReqCreateDTO cateGoryReqCreateDTO, Integer aid) throws CustomizeException {
        Categories categories = new Categories();
        BeanUtils.copyProperties(cateGoryReqCreateDTO, categories);
        categories.setCreateBy(aid);
        Categories saveResult;
        try {
            saveResult = categoryDBService.save(categories);
        } catch (Exception e) {
            throw new CustomizeException("创建失败");
        }

        return saveResult.getCid();
    }

    public List<Categories> findAll() {
        return categoryDBService.findAll();
    }

    public Integer update(CateGoryReqUpdateDTO cateGoryReqUpdateDTO, Integer updateBy) throws CustomizeException {
        Boolean isHas = categoryDBService.isHas(cateGoryReqUpdateDTO.getCid());
        if (isHas) {
            Categories categories = new Categories();
            BeanUtils.copyProperties(cateGoryReqUpdateDTO, categories);
            categories.setUpdateBy(updateBy);
            Categories categoriesResult = categoryDBService.update(categories);
            return categoriesResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }

    }

    public Integer patch(CateGoryReqPatchDTO cateGoryReqPatchDTO, Integer updateBy) throws CustomizeException {
        Boolean isHas = categoryDBService.isHas(cateGoryReqPatchDTO.getCid());
        if (isHas) {
            Categories categories = new Categories();
            BeanUtils.copyProperties(cateGoryReqPatchDTO, categories);
            categories.setUpdateBy(updateBy);
            Categories categoriesResult = categoryDBService.patch(categories);
            return categoriesResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }
    }

    public Integer delete(CateGoryReqDeleteDTO cateGoryReqDeleteDTO, Integer deleteBy) throws CustomizeException {
        Boolean isHas = categoryDBService.isHas(cateGoryReqDeleteDTO.getCid());
        if (isHas) {
            Categories categoryResult = categoryDBService.delete(cateGoryReqDeleteDTO.getCid(),deleteBy);
            return categoryResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }

    }

    public Categories findById(Integer cid) throws CustomizeException {
        try {
            return categoryDBService.findById(cid);
        } catch (Exception e) {
            throw new CustomizeException("不存在");
        }

    }
}
