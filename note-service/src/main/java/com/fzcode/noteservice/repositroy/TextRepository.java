package com.fzcode.noteservice.repositroy;

import com.fzcode.noteservice.entity.Columns;
import com.fzcode.noteservice.entity.Texts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TextRepository extends JpaRepository<Texts, Integer> {
    List<Texts> findByIsDelete(Boolean bool);
}
