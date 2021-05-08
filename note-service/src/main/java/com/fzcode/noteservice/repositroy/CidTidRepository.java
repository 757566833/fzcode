package com.fzcode.noteservice.repositroy;

import com.fzcode.noteservice.entity.CidTid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CidTidRepository extends JpaRepository<CidTid, Integer> {
    List<CidTid> findByCid(Integer cid);
    List<CidTid> findByTid(Integer tid);
}
