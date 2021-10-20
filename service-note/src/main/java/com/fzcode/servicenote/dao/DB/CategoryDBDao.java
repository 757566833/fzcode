package com.fzcode.servicenote.dao.DB;

import com.fzcode.servicenote.entity.CategoriesEntity;
import com.fzcode.servicenote.repositroy.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryDBDao {


    CategoryRepository categoryRepository;

    @Autowired
    public void setNoteRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public CategoriesEntity save(CategoriesEntity categoriesEntity) {
        CategoriesEntity categoriesEntityResult = categoryRepository.save(categoriesEntity);
        return categoriesEntityResult;
    }

    public List<CategoriesEntity> findAll() {
        List<CategoriesEntity> list = categoryRepository.findByIsDelete(0);
        return list;
    }

    public CategoriesEntity findById(Integer id) {
        Optional<CategoriesEntity> noteResult = categoryRepository.findById(id);
        return noteResult.get();
    }

    public CategoriesEntity update(CategoriesEntity categoriesEntity) {
        CategoriesEntity categoryResult = categoryRepository.save(categoriesEntity);
        return categoryResult;
    }

    public CategoriesEntity patch(CategoriesEntity categoriesEntity) {
        Integer cid = categoriesEntity.getCid();
        CategoriesEntity oldCategoriesEntity = this.findById(cid);
        CategoriesEntity newCategoriesEntity = new CategoriesEntity();
        BeanUtils.copyProperties(oldCategoriesEntity, newCategoriesEntity);
        BeanUtils.copyProperties(categoriesEntity, newCategoriesEntity);
        CategoriesEntity categoriesEntityResult = categoryRepository.save(newCategoriesEntity);
        return categoriesEntityResult;
    }

    public CategoriesEntity delete(Integer id, Integer deleteBy) {
        CategoriesEntity categoriesEntity = new CategoriesEntity();
        categoriesEntity.setIsDelete(1);
        categoriesEntity.setCid(id);
        categoriesEntity.setDeleteBy(deleteBy);
        CategoriesEntity categoriesEntityResult = categoryRepository.save(categoriesEntity);
        return categoriesEntityResult;
    }
    public Boolean isHas (Integer id){
        return categoryRepository.existsById(id);
    }
}
