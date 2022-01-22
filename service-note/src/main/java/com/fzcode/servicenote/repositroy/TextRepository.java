package com.fzcode.servicenote.repositroy;

import com.fzcode.servicenote.repositroy.mapper.TextDBFindListMapper;
import com.fzcode.servicenote.repositroy.mapper.TextDBFindListCountMapper;
import com.fzcode.servicenote.repositroy.mapper.TextDBGetByIdMapper;
import com.fzcode.servicenote.entity.Texts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TextRepository extends JpaRepository<Texts, Integer> {
//    List<Texts> findByIsDelete(Integer bool);
    @Query(nativeQuery = true, value = "SELECT " +
            "texts.create_by, " +
            "texts.update_time, " +
            "texts.title, " +
            "texts.type, " +
            "texts.text, " +
            "users.avatar, " +
            "users.username, " +
            "users.github_url " +
            "FROM " +
            "texts " +
            "JOIN users ON texts.create_by = users.uid " +
            "WHERE " +
            "texts.tid = ?1"
    )
    List<TextDBGetByIdMapper> findByIdWithUserInfo(Integer id);

    @Query(nativeQuery = true, value = "SELECT " +
            "texts.tid, " +
            "texts.description, " +
            "texts.is_delete, " +
            "texts.title, " +
            "texts.create_by, " +
            "texts.update_by, " +
            "texts.update_time, " +
            "texts.top, " +
            "users.uid, " +
            "users.username " +
            "FROM texts " +
            "JOIN users ON texts.create_by = users.uid " +
            "WHERE " +
            "texts.is_delete = 0 " +
            "ORDER BY " +
            "update_time DESC " +
            "LIMIT ?1 OFFSET ?2 "
    )
    List<TextDBFindListMapper> findList(Integer limit , Integer offset);
    @Query(nativeQuery = true, value = "SELECT " +
            "COUNT(texts.tid) as count " +
            "FROM " +
            "texts " +
            "JOIN users ON texts.create_by = users.uid " +
            "WHERE " +
            "texts.is_delete = 0 " +
            "LIMIT ?1 OFFSET ?2 "
    )
    List<TextDBFindListCountMapper> findListCount(Integer limit , Integer offset);

    Page<Texts> findByTitleContaining(String title, Pageable pageable);

//    long countByName(String name);
}
