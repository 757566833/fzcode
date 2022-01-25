package com.fzcode.serviceauth.repositroy;

import com.fzcode.serviceauth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, String> {
//    @Transactional
//    @Modifying
//    @Query(nativeQuery = true, value = "INSERT INTO accounts (account,create_time,delete_by,enabled,expired,is_delete,locked,PASSWORD,register_type,update_by,update_time) VALUES (?1,SYSDATE(),NULL,1,1,1,1,?2,1,NULL,SYSDATE())")
//    Integer createAccountWithUserInfo(String account, String password);

//    List<Users> findByIsDelete(Boolean bool);

      Users findFirstByUid(String uid);
      Users findFirstByAid(String aid);
}
