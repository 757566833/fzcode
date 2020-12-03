package com.fzcode.authservice.repositroy;

import com.fzcode.authservice.entity.Accounts;
import com.fzcode.authservice.entity.Authorities;
import com.fzcode.authservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authorities, Integer> {
//    @Transactional
//    @Modifying
//    @Query(nativeQuery = true, value = "INSERT INTO accounts (account,create_time,delete_by,enabled,expired,is_delete,locked,PASSWORD,register_type,update_by,update_time) VALUES (?1,SYSDATE(),NULL,1,1,1,1,?2,1,NULL,SYSDATE())")
//    Integer createAccountWithUserInfo(String account, String password);

//    List<Users> findByIsDelete(Boolean bool);
    Authorities findOneByAccount(String account);
}
