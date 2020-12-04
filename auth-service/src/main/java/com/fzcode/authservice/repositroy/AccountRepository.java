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

    Boolean existsByAccount(String account);
    @Query(nativeQuery = true, value = "SELECT users.username,accounts.account,accounts.enabled,accounts.expired,accounts.is_delete,accounts.locked,accounts.register_type,users.uid,users.avatar,users.update_by,users.update_time,accounts.create_time,accounts.delete_by,accounts.update_by AS account_update_by,accounts.update_time AS account_update_time FROM users,accounts WHERE users.github_url LIKE %?5% AND users.username LIKE %?3% AND accounts.account LIKE %?4% ORDER BY ?6 ASC,?7 DESC LIMIT ?1,?2")
    List<Accounts> findList(Integer offset, Integer length, String username, String account, String githubUrl, String desc, String asc);
}
