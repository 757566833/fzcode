package com.fzcode.authservice.repositroy;

import com.fzcode.authservice.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository extends JpaRepository<Accounts, Integer> {
//    @Transactional
//    @Modifying
//    @Query(nativeQuery = true, value = "INSERT INTO accounts (account,create_time,delete_by,enabled,expired,is_delete,locked,PASSWORD,register_type,update_by,update_time) VALUES (?1,SYSDATE(),NULL,1,1,1,1,?2,1,NULL,SYSDATE())")
//    Integer createAccountWithUserInfo(String account, String password);

    List<Accounts> findByIsDelete(Boolean bool);
    Accounts findOneByAccount(String account);
}
