package com.fzcode.authservice.service;

import com.fzcode.authservice.entity.Authorities;
import com.fzcode.authservice.repositroy.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorityService {

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
}
