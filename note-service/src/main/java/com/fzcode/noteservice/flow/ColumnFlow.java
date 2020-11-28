package com.fzcode.noteservice.flow;

import com.fzcode.noteservice.dto.request.Column.ColumnReqCreateDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqDeleteDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqPatchDTO;
import com.fzcode.noteservice.dto.request.Column.ColumnReqUpdateDTO;
import com.fzcode.noteservice.entity.Columns;
import com.fzcode.noteservice.exception.CustomizeException;
import com.fzcode.noteservice.service.DB.ColumnDBService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ColumnFlow {
    ColumnDBService columnDBService;

    @Autowired
    public void setColumnDBService(ColumnDBService columnDBService) {
        this.columnDBService = columnDBService;
    }

    public Integer create(ColumnReqCreateDTO columnReqCreateDTO) throws CustomizeException {
        Columns columns = new Columns();
        columns.setTitle(columnReqCreateDTO.getTitle());
        columns.setDescription(columnReqCreateDTO.getDescription());
        columns.setImg(columnReqCreateDTO.getImg());
        columns.setDetail(columnReqCreateDTO.getDetail());
        Columns saveResult;
        try {
            saveResult = columnDBService.save(columns);
        } catch (Exception e) {
            throw new CustomizeException("创建失败");
        }

        return saveResult.getCid();
    }

    public List<Columns> findAll() {
        return columnDBService.findAll();
    }

    public Integer update(ColumnReqUpdateDTO columnReqUpdateDTO) throws CustomizeException {
        Boolean isHas = columnDBService.isHas(columnReqUpdateDTO.getCid());
        if (isHas) {
            Columns columns = new Columns();
            BeanUtils.copyProperties(columnReqUpdateDTO, columns);
            Columns columnsResult = columnDBService.update(columns);
            return columnsResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }

    }

    public Integer patch(ColumnReqPatchDTO columnReqPatchDTO) throws CustomizeException {
        Boolean isHas = columnDBService.isHas(columnReqPatchDTO.getCid());
        if (isHas) {
            Columns columns = new Columns();
            BeanUtils.copyProperties(columnReqPatchDTO, columns);
            Columns columnsResult = columnDBService.patch(columns);
            return columnsResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public Integer delete(ColumnReqDeleteDTO columnReqDeleteDTO) throws CustomizeException {
        Boolean isHas = columnDBService.isHas(columnReqDeleteDTO.getCid());
        if (isHas) {
            Columns columnsResult = columnDBService.delete(columnReqDeleteDTO.getCid());
            return columnsResult.getCid();
        } else {
            throw new CustomizeException("不存在");
        }

    }

    public Columns findById(Integer cid) throws CustomizeException {
        try {
            return columnDBService.findById(cid);
        } catch (Exception e) {
            throw new CustomizeException("不存在");
        }

    }
}
