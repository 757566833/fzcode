package com.fzcode.authservice.repositroy;

import com.fzcode.authservice.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface AccountRepository extends JpaRepository<Accounts, Integer> {
//    @Transactional
//    @Modifying
//    @Query(nativeQuery = true, value = "INSERT INTO accounts (account,create_time,delete_by,enabled,expired,is_delete,locked,PASSWORD,register_type,update_by,update_time) VALUES (?1,SYSDATE(),NULL,1,1,1,1,?2,1,NULL,SYSDATE())")
//    Integer createAccountWithUserInfo(String account, String password);

    List<Accounts> findByIsDelete(Boolean bool);

    Accounts findOneByAccount(String account);

    Boolean existsByAccount(String account);

    @Query(nativeQuery = true, value = "SELECT users.username,accounts.account,accounts.enabled,accounts.expired,accounts.is_delete,accounts.locked,accounts.register_type,users.uid,users.avatar,users.github_url,users.update_by,users.update_time,accounts.create_time,accounts.delete_by " +
            "FROM users,accounts " +
            "WHERE " +
            "if(?3!=null,users.username LIKE CONCAT('%',?3,'%'),1=1) " +
            "AND if(?4!=null,accounts.account LIKE CONCAT('%',?4,'%'),1=1) " +
            "AND if(?5!=null,users.github_url LIKE CONCAT('%',?5,'%'),1=1) " +
            "ORDER BY " +
            "if(?6!=null,CONCAT(?6,' DESC'),1=1),if(?7!=null,CONCAT(?7,' ASC'),1=1)" +
            "LIMIT ?1,?2")
    List<Map<String, Object>> findList(Integer offset, Integer length, String username, String account, String githubUrl, String desc, String asc);
}
