package com.fzcode.servicenote.service;

import com.fzcode.internalcommon.constant.AuthorityConstant;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryRequest;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.servicenote.entity.Categories;
import com.fzcode.servicenote.dao.DB.CategoryDBDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    CategoryDBDao categoryDBDao;

    @Autowired
    public void setColumnDBService(CategoryDBDao categoryDBDao) {
        this.categoryDBDao = categoryDBDao;
    }

    public Integer create(CategoryRequest cateGoryReqCreateDTO, Integer aid, String authority) throws CustomizeException {
        if(!authority.equals(AuthorityConstant.admin)){
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"权限不足");
        }
        Categories categories = new Categories();
        BeanUtils.copyProperties(cateGoryReqCreateDTO, categories);
        categories.setCreateBy(aid);
        Categories saveResult;
        System.out.println("create");
        try {
            saveResult = categoryDBDao.save(categories);
        } catch (Exception e) {
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"创建失败");
        }

        return saveResult.getCid();
    }

    public List<Categories> findAll() {
        return categoryDBDao.findAll();
    }

    public Integer update(CategoryRequest categoryRequest, Integer updateBy) throws CustomizeException {
        Boolean isHas = categoryDBDao.isHas(categoryRequest.getCid());
        if (isHas) {
            Categories categories = new Categories();
            BeanUtils.copyProperties(categoryRequest, categories);
            categories.setUpdateBy(updateBy);
            Categories categoriesResult = categoryDBDao.update(categories);
            return categoriesResult.getCid();
        } else {
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"不存在");
        }

    }

    public Integer patch(CategoryRequest categoryRequest, Integer updateBy) throws CustomizeException {
        Boolean isHas = categoryDBDao.isHas(categoryRequest.getCid());
        if (isHas) {
            Categories categories = new Categories();
            BeanUtils.copyProperties(categoryRequest, categories);
            categories.setUpdateBy(updateBy);
            Categories categoriesResult = categoryDBDao.patch(categories);
            return categoriesResult.getCid();
        } else {
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"不存在");
        }
    }

    public Integer delete(CategoryRequest categoryRequest, Integer deleteBy) throws CustomizeException {
        Boolean isHas = categoryDBDao.isHas(categoryRequest.getCid());
        if (isHas) {
            Categories categoryResult = categoryDBDao.delete(categoryRequest.getCid(),deleteBy);
            return categoryResult.getCid();
        } else {
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"不存在");
        }

    }

    public Categories findById(Integer cid) throws CustomizeException {
        try {
            return categoryDBDao.findById(cid);
        } catch (Exception e) {
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"不存在");
        }

    }
}
