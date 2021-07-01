package com.fzcode.serviceauth.dao;

import com.fzcode.serviceauth.entity.Users;
import com.fzcode.serviceauth.repositroy.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Page<Users> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Users> list = userRepository.findAll(pageable);
        return list;
    }

    public Users findById(Integer id) {
        return userRepository.findById(id).get();
    }

    public Users create(Users users) {
        return userRepository.save(users);
    }


    public Users update(Users users) {
        return userRepository.save(users);
    }

    public Users patch(Users users) {
        Integer uid = users.getUid();
        Users oldUsers = this.findById(uid);
        Users newUsers = new Users();
        BeanUtils.copyProperties(oldUsers, newUsers);
        BeanUtils.copyProperties(users, newUsers);
        return userRepository.save(newUsers);
    }

}
