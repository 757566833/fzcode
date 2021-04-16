package com.fzcode.noteservice.repositroy;

import com.fzcode.noteservice.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findByIsDelete(Integer bool);
}
