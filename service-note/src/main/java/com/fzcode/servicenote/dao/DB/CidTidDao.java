package com.fzcode.servicenote.dao.DB;

import com.fzcode.servicenote.entity.CidTid;
import com.fzcode.servicenote.repositroy.CidTidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CidTidDao {


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
    public List<CidTid> getListByTid(Collection<Integer> ids) {
        return cidTidRepository.findByTidIn(ids);
    }
}
