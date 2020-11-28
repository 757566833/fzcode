package com.fzcode.noteservice.repositroy;

import com.fzcode.noteservice.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumnRepository extends JpaRepository<Columns, Integer> {
    List<Columns> findByIsDelete(Boolean bool);
}
