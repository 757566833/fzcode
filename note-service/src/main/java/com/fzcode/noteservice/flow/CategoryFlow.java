package com.fzcode.noteservice.flow;

import com.fzcode.noteservice.dto.request.Column.ColumnReqCreateDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqDeleteDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqPatchDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqUpdateDTO;
import com.fzcode.noteservice.entity.Categorys;
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

    public Integer create(ColumnReqCreateDTO columnReqCreateDTO,Integer aid) throws CustomizeException {
        Categorys categorys = new Categorys();
        BeanUtils.copyProperties(columnReqCreateDTO, categorys);
        categorys.setCreateBy(aid);
        Categorys saveResult;
        try {
            saveResult = categoryDBService.save(categorys);
        } catch (Exception e) {
            throw new CustomizeException("创建失败");
        }

        return saveResult.getCid();
    }

    public List<Categorys> findAll() {
        return categoryDBService.findAll();
    }

    public Integer update(ColumnReqUpdateDTO columnReqUpdateDTO,Integer updateBy) throws CustomizeException {
        Boolean isHas = categoryDBService.isHas(columnReqUpdateDTO.getCid());
        if (isHas) {
            Categorys categorys = new Categorys();
            BeanUtils.copyProperties(columnReqUpdateDTO, categorys);
            categorys.setUpdateBy(updateBy);
            Categorys categorysResult = categoryDBService.update(categorys);
            return categorysResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }

    }

    public Integer patch(ColumnReqPatchDTO columnReqPatchDTO,Integer updateBy) throws CustomizeException {
        Boolean isHas = categoryDBService.isHas(columnReqPatchDTO.getCid());
        if (isHas) {
            Categorys categorys = new Categorys();
            BeanUtils.copyProperties(columnReqPatchDTO, categorys);
            categorys.setUpdateBy(updateBy);
            Categorys categorysResult = categoryDBService.patch(categorys);
            return categorysResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }
    }

    public Integer delete(ColumnReqDeleteDTO columnReqDeleteDTO,Integer deleteBy) throws CustomizeException {
        Boolean isHas = categoryDBService.isHas(columnReqDeleteDTO.getCid());
        if (isHas) {
            Categorys categoryResult = categoryDBService.delete(columnReqDeleteDTO.getCid(),deleteBy);
            return categoryResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }

    }

    public Categorys findById(Integer cid) throws CustomizeException {
        try {
            return categoryDBService.findById(cid);
        } catch (Exception e) {
            throw new CustomizeException("不存在");
        }

    }
}
