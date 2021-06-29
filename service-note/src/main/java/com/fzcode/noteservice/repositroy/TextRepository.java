package com.fzcode.noteservice.repositroy;

import com.fzcode.noteservice.repositroy.dbInterface.text.TextDBFindList;
import com.fzcode.noteservice.repositroy.dbInterface.text.TextDBFindListCount;
import com.fzcode.noteservice.repositroy.dbInterface.text.TextDBGetById;
import com.fzcode.noteservice.entity.Texts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TextRepository extends JpaRepository<Texts, Integer> {
//    List<Texts> findByIsDelete(Integer bool);
    @Query(nativeQuery = true, value = "SELECT " +
            "texts.create_by, " +
            "texts.update_time, " +
            "texts.title, " +
            "texts.html, " +
            "texts.raw, " +
            "users.avatar, " +
            "users.username, " +
            "users.github_url " +
            "FROM " +
            "texts " +
            "JOIN users ON texts.create_by = users.uid " +
            "WHERE " +
            "texts.tid = ?1"
    )
    List<TextDBGetById> findByIdWithUserInfo(Integer id);

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
    List<TextDBFindList> findList(Integer limit , Integer offset);
    @Query(nativeQuery = true, value = "SELECT " +
            "COUNT(texts.tid) as count " +
            "FROM " +
            "texts " +
            "JOIN users ON texts.create_by = users.uid " +
            "WHERE " +
            "texts.is_delete = 0 " +
            "LIMIT ?1 OFFSET ?2 "
    )
    List<TextDBFindListCount> findListCount(Integer limit , Integer offset);
}
