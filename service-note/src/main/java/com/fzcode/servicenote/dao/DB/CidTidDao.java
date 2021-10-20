package com.fzcode.servicenote.dao.DB;

import com.fzcode.servicenote.entity.CidTidEntity;
import com.fzcode.servicenote.repositroy.CidTidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidTidDao {


    CidTidRepository cidTidRepository;

    @Autowired
    public void setCidTidRepository(CidTidRepository cidTidRepository) {
        this.cidTidRepository = cidTidRepository;
    }


    public CidTidEntity save(CidTidEntity cidTidEntity) {
        CidTidEntity cidTidEntityRes = cidTidRepository.save(cidTidEntity);
        return cidTidEntityRes;
    }
    public List<CidTidEntity> saveAll(Iterable<CidTidEntity> list) {
         List<CidTidEntity> l = cidTidRepository.saveAll(list);
        return l;
    }
    public List<CidTidEntity> getByCid(Integer cid) {
        List<CidTidEntity> list = cidTidRepository.findByCid(cid);
        return list;
    }
    public List<CidTidEntity> getByTid(Integer tid) {
        List<CidTidEntity> list = cidTidRepository.findByTid(tid);
        return list;
    }
}
