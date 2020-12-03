package com.fzcode.authservice.service;

import com.fzcode.authservice.entity.Accounts;
import com.fzcode.authservice.repositroy.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Accounts create(Accounts accounts) {

        return accountRepository.save(accounts);
    }

    public Page<Accounts> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
//        Page page1 = new Page<>()
        Page<Accounts> accounts = accountRepository.findAll(pageable);
        return accounts;
    }

    public Accounts findByAccount(String account) {
        return accountRepository.findOneByAccount(account);

    }

    public boolean isHas(String email) {
        Accounts accounts = new Accounts();
        accounts.setAccount(email);
        Example<Accounts> example = Example.of(accounts);
        Optional<Accounts> optional = accountRepository.findOne(example);
        Accounts current = optional.get();
        if (current != null) {
            return true;
        } else {
            return false;

        }
//        return accountRepository.findOne(example);
    }
}
