package com.fzcode.authservice.service;

import com.alibaba.fastjson.JSON;
import com.fzcode.authservice.dto.common.ListResDTO;
import com.fzcode.authservice.dto.request.list.AccountDTO;
import com.fzcode.authservice.entity.Accounts;
import com.fzcode.authservice.entity.Users;
import com.fzcode.authservice.exception.CustomizeException;
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

    public ListResDTO<List<Map<String, Object>>> findList(AccountDTO accountDTO) {
        Integer offset = (accountDTO.getPage() - 1) * accountDTO.getPageSize();
        Integer length = accountDTO.getPageSize();
        String desc = null;
        Boolean descInAccount = false;
        System.out.println("desc:" + accountDTO.getDesc());
        try {
            Field a = Accounts.class.getDeclaredField(accountDTO.getDesc());
            descInAccount = a.getName().equals(accountDTO.getDesc());
        } catch (Exception e) {
//             e.printStackTrace();
        }
        System.out.println("descInAccount:" + descInAccount);
        if (descInAccount) {
            desc = "accounts." + accountDTO.getDesc();
        } else {
            Boolean ascInUser = false;
            try {
                ascInUser = Users.class.getDeclaredField(accountDTO.getDesc()).getName().equals(accountDTO.getDesc());
            } catch (Exception e) {
//                 e.printStackTrace();
            }
            if (ascInUser) {
                desc = "users." + accountDTO.getDesc();
            }
        }
        String asc = null;
        Boolean ascInAccount = false;
        try {
            ascInAccount = Accounts.class.getDeclaredField(accountDTO.getDesc()).getName().equals(accountDTO.getAsc());
        } catch (Exception e) {
//             e.printStackTrace();
        }
        if (ascInAccount) {
            asc = "accounts." + accountDTO.getAsc();
        } else {
            Boolean ascInUser = false;
            try {
                ascInUser = Users.class.getDeclaredField(accountDTO.getDesc()).getName().equals(accountDTO.getAsc());

            } catch (Exception e) {
//                e.printStackTrace();
            }
            if (ascInUser) {
                asc = "users." + accountDTO.getAsc();
            }
        }
        List<Map<String, Object>> accounts = accountRepository.findList(offset, length, accountDTO.getUsername(), accountDTO.getAccount(), accountDTO.getGithubUrl(), desc, asc);
        List<Map<String, Object>> countList = accountRepository.findListCount(accountDTO.getUsername(), accountDTO.getAccount(), accountDTO.getGithubUrl());
        System.out.println(JSON.toJSONString(countList.get(0)));
        Object count = countList.get(0).get("COUNT(1)").toString();
        ListResDTO<List<Map<String, Object>>> listListResDTO = new ListResDTO<>();
        listListResDTO.setCount(count);
        listListResDTO.setList(accounts);
        listListResDTO.setPage(accountDTO.getPage());
        listListResDTO.setPageSize(accountDTO.getPageSize());
        return listListResDTO;
    }

    public Accounts findByAccount(String account) {
        return accountRepository.findOneByAccount(account);

    }

    public Map<String, Object> findUserInfoByUid(Integer aid) throws CustomizeException {
        List<Map<String, Object>> mapList = accountRepository.findUserInfoByUid(aid);
        if (mapList.size() == 0) {
            throw new CustomizeException("用户不存在");
        }
        return mapList.get(0);

    }

    public boolean isHas(String email) {
        Boolean bool = accountRepository.existsByAccount(email);
        System.out.println("isHas:" + bool);
        return bool;
//        return accountRepository.findOne(example);
    }
}
