package com.fzcode.elasticservice.repositroy;

import com.fzcode.elasticservice.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoteRepository extends JpaRepository<Notes, Integer> {
    @Modifying
    @Query("update Notes set title = ?2 where nid = ?1")
    Notes patch(Integer nid, String title);

    @Modifying
    @Query("update Notes set description = ?1 where nid = ?2")
    Notes patch(String description, Integer nid);

    @Modifying
    @Query("update Notes set title = ?2,description = ?3 where nid = ?1")
    Notes patch(Integer nid, String title, String description);
}
