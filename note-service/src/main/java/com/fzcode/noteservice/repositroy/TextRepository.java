package com.fzcode.noteservice.repositroy;

import com.fzcode.noteservice.DBInterface.TextDBGetDTO;
import com.fzcode.noteservice.entity.Texts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TextRepository extends JpaRepository<Texts, Integer> {
//    List<Texts> findByIsDelete(Integer bool);
    @Query(nativeQuery = true, value = "SELECT texts.tid,texts.cid,texts.create_by,texts.create_time,texts.description,texts.tags,texts.text,texts.title,texts.update_time,users.avatar,users.username,categorys.title as category " +
            "FROM texts " +
            "JOIN users "+
            "ON texts.create_by=users.uid "+
            "JOIN categorys "+
            "ON texts.cid=categorys.cid "+
            "WHERE " +
            "texts.tid = ?1 "
    )
    List<TextDBGetDTO> findByIdWithUserInfo(Integer id);
}
