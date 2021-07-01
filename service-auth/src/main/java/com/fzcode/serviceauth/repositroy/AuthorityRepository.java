package com.fzcode.serviceauth.repositroy;

import com.fzcode.serviceauth.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorityRepository extends JpaRepository<Authorities, Integer> {
//    @Transactional
//    @Modifying
//    @Query(nativeQuery = true, value = "INSERT INTO accounts (account,create_time,delete_by,enabled,expired,is_delete,locked,PASSWORD,register_type,update_by,update_time) VALUES (?1,SYSDATE(),NULL,1,1,1,1,?2,1,NULL,SYSDATE())")
//    Integer createAccountWithUserInfo(String account, String password);

//    List<Users> findByIsDelete(Boolean bool);
    Authorities findOneByAccount(String account);
    @Query(nativeQuery = true, value = "UPDATE authorities SET authority = ?2 WHERE account = ?1")
    Authorities updateByAccount(String account,String authority);
}
