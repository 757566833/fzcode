package com.fzcode.serviceauth.dao;

import com.fzcode.serviceauth.entity.Authorities;
import com.fzcode.serviceauth.repositroy.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorityDao {

    private AuthorityRepository authorityRepository;

    @Autowired
    public void setAccountRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authorities create(Authorities authorities) {
        return authorityRepository.save(authorities);
    }

    public Authorities findByAccount(String account) {
        return authorityRepository.findOneByAccount(account);
    }

    public List<Authorities> findAll() {
        return authorityRepository.findAll();
    }

    public Authorities update(String account,String authority) {
        return authorityRepository.updateByAccount(account,authority);
    }
}
