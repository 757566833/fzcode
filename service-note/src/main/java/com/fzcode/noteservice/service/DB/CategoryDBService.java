package com.fzcode.noteservice.service.DB;

import com.fzcode.noteservice.entity.Categories;
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


    public Categories save(Categories categories) {
        Categories categoriesResult = categoryRepository.save(categories);
        return categoriesResult;
    }

    public List<Categories> findAll() {
        List<Categories> list = categoryRepository.findByIsDelete(0);
        return list;
    }

    public Categories findById(Integer id) {
        Optional<Categories> noteResult = categoryRepository.findById(id);
        return noteResult.get();
    }

    public Categories update(Categories categories) {
        Categories categoryResult = categoryRepository.save(categories);
        return categoryResult;
    }

    public Categories patch(Categories categories) {
        Integer cid = categories.getCid();
        Categories oldCategories = this.findById(cid);
        Categories newCategories = new Categories();
        BeanUtils.copyProperties(oldCategories, newCategories);
        BeanUtils.copyProperties(categories, newCategories);
        Categories categoriesResult = categoryRepository.save(newCategories);
        return categoriesResult;
    }

    public Categories delete(Integer id, Integer deleteBy) {
        Categories categories = new Categories();
        categories.setIsDelete(1);
        categories.setCid(id);
        categories.setDeleteBy(deleteBy);
        Categories categoriesResult = categoryRepository.save(categories);
        return categoriesResult;
    }
    public Boolean isHas (Integer id){
        return categoryRepository.existsById(id);
    }
}
