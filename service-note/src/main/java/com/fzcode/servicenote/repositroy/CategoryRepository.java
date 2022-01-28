package com.fzcode.servicenote.repositroy;

import com.fzcode.servicenote.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findByIsDelete(Integer bool);

    List<Categories> findByCidIn(Collection<Integer> ids);
}
