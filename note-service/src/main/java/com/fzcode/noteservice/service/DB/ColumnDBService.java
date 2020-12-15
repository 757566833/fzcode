package com.fzcode.noteservice.service.DB;

import com.fzcode.noteservice.entity.Columns;
import com.fzcode.noteservice.repositroy.ColumnRepository;
import org.hibernate.criterion.Example;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColumnDBService {


    ColumnRepository columnRepository;

    @Autowired
    public void setNoteRepository(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }


    public Columns save(Columns columns) {
        Columns columnsResult = columnRepository.save(columns);
        return columnsResult;
    }

    public List<Columns> findAll() {
        List<Columns> list = columnRepository.findByIsDelete(0);
        return list;
    }

    public Columns findById(Integer id) {
        Optional<Columns> noteResult = columnRepository.findById(id);
        return noteResult.get();
    }

    public Columns update(Columns columns) {
        Columns columnsResult = columnRepository.save(columns);
        return columnsResult;
    }

    public Columns patch(Columns columns) {
        Integer cid = columns.getCid();
        Columns oldColumns = this.findById(cid);
        Columns newColumns = new Columns();
        BeanUtils.copyProperties(oldColumns,newColumns);
        BeanUtils.copyProperties(columns,newColumns);
        Columns columnsResult = columnRepository.save(newColumns);
        return columnsResult;
    }

    public Columns delete(Integer id,Integer deleteBy) {
        Columns columns = new Columns();
        columns.setIsDelete(1);
        columns.setCid(id);
        columns.setDeleteBy(deleteBy);
        Columns columnsResult = columnRepository.save(columns);
        return columnsResult;
    }
    public Boolean isHas (Integer id){
        return columnRepository.existsById(id);
    }
}
