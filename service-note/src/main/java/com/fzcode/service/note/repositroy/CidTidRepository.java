package com.fzcode.service.note.repositroy;

import com.fzcode.service.note.entity.CidTid;
import com.fzcode.service.note.repositroy.dbInterface.cidTid.CidTidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CidTidRepository extends JpaRepository<CidTid, Integer> {
    List<CidTid> findByCid(Integer cid);
    List<CidTid> findByTid(Integer tid);
    
    @Query(nativeQuery = true, value = "SELECT " +
            "tbl_cid_tid.tid, " +
            "GROUP_CONCAT( tbl_cid_tid.cid SEPARATOR \",\" ) AS cidList " +
            "FROM " +
            "tbl_cid_tid " +
            "JOIN categories ON tbl_cid_tid.cid = categories.cid " +
            "WHERE " +
            "tbl_cid_tid.tid IN ( ?1 ) " +
            "GROUP BY " +
            "tid "
    )
    List<CidTidList> findList(String tidString);
}
