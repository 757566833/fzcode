package com.fzcode.noteservice.repositroy;

import com.fzcode.noteservice.entity.Categorys;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Categorys, Integer> {
    List<Categorys> findByIsDelete(Integer bool);
}
