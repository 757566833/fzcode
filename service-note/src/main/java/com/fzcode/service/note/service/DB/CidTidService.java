package com.fzcode.service.note.service.DB;

import com.fzcode.service.note.entity.CidTid;
import com.fzcode.service.note.repositroy.CidTidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
