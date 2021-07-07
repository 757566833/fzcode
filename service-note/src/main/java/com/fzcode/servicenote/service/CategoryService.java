package com.fzcode.servicenote.service;

import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryCreateRequest;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryDeleteRequest;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryPatchRequest;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryUpdateRequest;
import com.fzcode.servicenote.entity.Categories;
import com.fzcode.servicenote.exception.CustomizeException;
import com.fzcode.servicenote.dao.DB.CategoryDBDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    CategoryDBDao categoryDBDao;

    @Autowired
    public void setColumnDBService(CategoryDBDao categoryDBDao) {
        this.categoryDBDao = categoryDBDao;
    }

    public Integer create(CategoryCreateRequest cateGoryReqCreateDTO, Integer aid) throws CustomizeException {
        Categories categories = new Categories();
        BeanUtils.copyProperties(cateGoryReqCreateDTO, categories);
        categories.setCreateBy(aid);
        Categories saveResult;
        try {
            saveResult = categoryDBDao.save(categories);
        } catch (Exception e) {
            throw new CustomizeException("创建失败");
        }

        return saveResult.getCid();
    }

    public List<Categories> findAll() {
        return categoryDBDao.findAll();
    }

    public Integer update(CategoryUpdateRequest categoryUpdateRequest, Integer updateBy) throws CustomizeException {
        Boolean isHas = categoryDBDao.isHas(categoryUpdateRequest.getCid());
        if (isHas) {
            Categories categories = new Categories();
            BeanUtils.copyProperties(categoryUpdateRequest, categories);
            categories.setUpdateBy(updateBy);
            Categories categoriesResult = categoryDBDao.update(categories);
            return categoriesResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }

    }

    public Integer patch(CategoryPatchRequest categoryPatchRequest, Integer updateBy) throws CustomizeException {
        Boolean isHas = categoryDBDao.isHas(categoryPatchRequest.getCid());
        if (isHas) {
            Categories categories = new Categories();
            BeanUtils.copyProperties(categoryPatchRequest, categories);
            categories.setUpdateBy(updateBy);
            Categories categoriesResult = categoryDBDao.patch(categories);
            return categoriesResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }
    }

    public Integer delete(CategoryDeleteRequest categoryDeleteRequest, Integer deleteBy) throws CustomizeException {
        Boolean isHas = categoryDBDao.isHas(categoryDeleteRequest.getCid());
        if (isHas) {
            Categories categoryResult = categoryDBDao.delete(categoryDeleteRequest.getCid(),deleteBy);
            return categoryResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }

    }

    public Categories findById(Integer cid) throws CustomizeException {
        try {
            return categoryDBDao.findById(cid);
        } catch (Exception e) {
            throw new CustomizeException("不存在");
        }

    }
}
