package com.fzcode.authservice.service;

import com.fzcode.authservice.dto.request.list.AccountDTO;
import com.fzcode.authservice.entity.Accounts;
import com.fzcode.authservice.repositroy.AccountRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public Page<Accounts> findAll(Integer page, Integer size, String asc, String desc) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(asc).ascending().and(Sort.by(desc).descending()));
        Page<Accounts> accounts = accountRepository.findAll(pageable);
        return accounts;
    }

    public List<Accounts> findList(AccountDTO accountDTO) {
        Integer offset = (accountDTO.getPage() - 1) * accountDTO.getSize();
        Integer length = accountDTO.getSize();
        List<Accounts> accounts = accountRepository.findList(offset, length, accountDTO.getUsername(), accountDTO.getAccount(), accountDTO.getGithubUrl(), accountDTO.getDesc(), accountDTO.getAsc());
        return accounts;
    }

    public Accounts findByAccount(String account) {
        return accountRepository.findOneByAccount(account);

    }

    public boolean isHas(String email) {
        Boolean bool = accountRepository.existsByAccount(email);
        System.out.println("isHas:" + bool);
        return bool;
//        return accountRepository.findOne(example);
    }
}
