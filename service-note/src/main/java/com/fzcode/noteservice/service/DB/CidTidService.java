package com.fzcode.noteservice.service.DB;

import com.fzcode.noteservice.entity.Categories;
import com.fzcode.noteservice.entity.CidTid;
import com.fzcode.noteservice.repositroy.CategoryRepository;
import com.fzcode.noteservice.repositroy.CidTidRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidTidService {


    CidTidRepository cidTidRepository;

    @Autowired
    public void setCidTidRepository(CidTidRepository cidTidRepository) {
        this.cidTidRepository = cidTidRepository;
    }


    public CidTid save(CidTid cidTid) {
        CidTid cidTidRes = cidTidRepository.save(cidTid);
        return cidTidRes;
    }
    public List<CidTid> saveAll(Iterable<CidTid> list) {
         List<CidTid> l = cidTidRepository.saveAll(list);
        return l;
    }
    public List<CidTid> getByCid(Integer cid) {
        List<CidTid> list = cidTidRepository.findByCid(cid);
        return list;
    }
    public List<CidTid> getByTid(Integer tid) {
        List<CidTid> list = cidTidRepository.findByTid(tid);
        return list;
    }
}
