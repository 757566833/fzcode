package com.fzcode.servicenote.service;

import com.fzcode.internalcommon.constant.AuthorityConstant;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryCreateRequest;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryDeleteRequest;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryPatchRequest;
import com.fzcode.internalcommon.dto.servicenote.request.category.CategoryUpdateRequest;
import com.fzcode.servicenote.entity.CategoriesEntity;
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

    public Integer create(CategoryCreateRequest cateGoryReqCreateDTO, Integer aid,String authority) throws CustomizeException {
        if(authority!= AuthorityConstant.admin){
            throw new CustomizeException("权限不足");
        }
        CategoriesEntity categoriesEntity = new CategoriesEntity();
        BeanUtils.copyProperties(cateGoryReqCreateDTO, categoriesEntity);
        categoriesEntity.setCreateBy(aid);
        CategoriesEntity saveResult;
        System.out.println("create");
        try {
            saveResult = categoryDBDao.save(categoriesEntity);
        } catch (Exception e) {
            throw new CustomizeException("创建失败");
        }

        return saveResult.getCid();
    }

    public List<CategoriesEntity> findAll() {
        return categoryDBDao.findAll();
    }

    public Integer update(CategoryUpdateRequest categoryUpdateRequest, Integer updateBy) throws CustomizeException {
        Boolean isHas = categoryDBDao.isHas(categoryUpdateRequest.getCid());
        if (isHas) {
            CategoriesEntity categoriesEntity = new CategoriesEntity();
            BeanUtils.copyProperties(categoryUpdateRequest, categoriesEntity);
            categoriesEntity.setUpdateBy(updateBy);
            CategoriesEntity categoriesEntityResult = categoryDBDao.update(categoriesEntity);
            return categoriesEntityResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }

    }

    public Integer patch(CategoryPatchRequest categoryPatchRequest, Integer updateBy) throws CustomizeException {
        Boolean isHas = categoryDBDao.isHas(categoryPatchRequest.getCid());
        if (isHas) {
            CategoriesEntity categoriesEntity = new CategoriesEntity();
            BeanUtils.copyProperties(categoryPatchRequest, categoriesEntity);
            categoriesEntity.setUpdateBy(updateBy);
            CategoriesEntity categoriesEntityResult = categoryDBDao.patch(categoriesEntity);
            return categoriesEntityResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }
    }

    public Integer delete(CategoryDeleteRequest categoryDeleteRequest, Integer deleteBy) throws CustomizeException {
        Boolean isHas = categoryDBDao.isHas(categoryDeleteRequest.getCid());
        if (isHas) {
            CategoriesEntity categoryResult = categoryDBDao.delete(categoryDeleteRequest.getCid(),deleteBy);
            return categoryResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }

    }

    public CategoriesEntity findById(Integer cid) throws CustomizeException {
        try {
            return categoryDBDao.findById(cid);
        } catch (Exception e) {
            throw new CustomizeException("不存在");
        }

    }
}
