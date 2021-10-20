package com.fzcode.servicenote.repositroy;

import com.fzcode.servicenote.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoriesEntity, Integer> {
    List<CategoriesEntity> findByIsDelete(Integer bool);
}
