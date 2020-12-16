package com.fzcode.noteservice.service.DB;

import com.fzcode.noteservice.entity.Categorys;
import com.fzcode.noteservice.repositroy.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryDBService {


    CategoryRepository categoryRepository;

    @Autowired
    public void setNoteRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Categorys save(Categorys categorys) {
        Categorys categorysResult = categoryRepository.save(categorys);
        return categorysResult;
    }

    public List<Categorys> findAll() {
        List<Categorys> list = categoryRepository.findByIsDelete(0);
        return list;
    }

    public Categorys findById(Integer id) {
        Optional<Categorys> noteResult = categoryRepository.findById(id);
        return noteResult.get();
    }

    public Categorys update(Categorys categorys) {
        Categorys categoryResult = categoryRepository.save(categorys);
        return categoryResult;
    }

    public Categorys patch(Categorys categorys) {
        Integer cid = categorys.getCid();
        Categorys oldCategorys = this.findById(cid);
        Categorys newCategorys = new Categorys();
        BeanUtils.copyProperties(oldCategorys, newCategorys);
        BeanUtils.copyProperties(categorys, newCategorys);
        Categorys categorysResult = categoryRepository.save(newCategorys);
        return categorysResult;
    }

    public Categorys delete(Integer id, Integer deleteBy) {
        Categorys categorys = new Categorys();
        categorys.setIsDelete(1);
        categorys.setCid(id);
        categorys.setDeleteBy(deleteBy);
        Categorys categorysResult = categoryRepository.save(categorys);
        return categorysResult;
    }
    public Boolean isHas (Integer id){
        return categoryRepository.existsById(id);
    }
}
