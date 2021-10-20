package com.fzcode.servicenote.repositroy;

import com.fzcode.servicenote.entity.CidTidEntity;
import com.fzcode.servicenote.repositroy.mapper.CidTidMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CidTidRepository extends JpaRepository<CidTidEntity, Integer> {
    List<CidTidEntity> findByCid(Integer cid);
    List<CidTidEntity> findByTid(Integer tid);
    
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
    List<CidTidMapper> findList(String tidString);
}
