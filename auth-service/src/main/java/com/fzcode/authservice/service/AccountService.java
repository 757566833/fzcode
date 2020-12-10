package com.fzcode.authservice.service;

import com.fzcode.authservice.dto.request.list.AccountDTO;
import com.fzcode.authservice.entity.Accounts;
import com.fzcode.authservice.entity.Users;
import com.fzcode.authservice.repositroy.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;


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

    public List<Map<String, Object>> findList(AccountDTO accountDTO) {
        Integer offset = (accountDTO.getPage() - 1) * accountDTO.getSize();
        Integer length = accountDTO.getSize();
        String desc = null;
        Boolean descInAccount = false;
        System.out.println("desc:" + accountDTO.getDesc());
        try {
            Field a =  Accounts.class.getDeclaredField(accountDTO.getDesc());
            descInAccount = a.getName().equals(accountDTO.getDesc());
        } catch (Exception e) {
             e.printStackTrace();
        }
        System.out.println("descInAccount:" + descInAccount);
        if (descInAccount) {
            desc = "accounts." + accountDTO.getDesc();
        } else {
            Boolean ascInUser = false;
            try {
                ascInUser = Users.class.getDeclaredField(accountDTO.getDesc()).getName().equals(accountDTO.getDesc());
            } catch (Exception e) {
                 e.printStackTrace();
            }
            if (ascInUser) {
                desc = "users." + accountDTO.getDesc();
            }
        }
        String asc = null;
        Boolean ascInAccount = false;
        try {
            ascInAccount =Accounts.class.getDeclaredField(accountDTO.getDesc()).getName().equals(accountDTO.getAsc());
        } catch (Exception e) {
             e.printStackTrace();
        }
        if (ascInAccount) {
            asc = "accounts." + accountDTO.getAsc();
        } else {
            Boolean ascInUser = false;
            try {
                ascInUser = Users.class.getDeclaredField(accountDTO.getDesc()).getName().equals(accountDTO.getAsc());

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ascInUser) {
                asc = "users." + accountDTO.getAsc();
            }
        }
        List<Map<String, Object>> accounts = accountRepository.findList(offset, length, accountDTO.getUsername(), accountDTO.getAccount(), accountDTO.getGithubUrl(), desc, asc);
//        List<Map<String,Object>> accounts = accountRepository.findList(offset, length);
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
