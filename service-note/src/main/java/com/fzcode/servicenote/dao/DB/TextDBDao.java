package com.fzcode.servicenote.dao.DB;

import com.fzcode.internalcommon.dto.common.ListDTO;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.servicenote.repositroy.CategoryRepository;
import com.fzcode.servicenote.repositroy.CidTidRepository;
import com.fzcode.servicenote.entity.Texts;
import com.fzcode.servicenote.repositroy.TextRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TextDBDao {


    TextRepository textRepository;

    @Autowired
    public void setNoteRepository(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    CidTidRepository cidTidRepository;

    @Autowired
    public void setCidTidRepository(CidTidRepository cidTidRepository) {
        this.cidTidRepository = cidTidRepository;
    }

    CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Texts save(Texts texts) {
        Texts textsResult = textRepository.save(texts);
        return textsResult;
    }

    public ListDTO<Texts> findByPage(Integer page, Integer size) {
        ListDTO<Texts> listDTO = new ListDTO<>();
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Texts> texts = textRepository.findAll(pageable);
        listDTO.setList(texts.toList());
        Texts item = new Texts();
        Long count = textRepository.count(Example.of(item));
        listDTO.setCount(count);
        listDTO.setPage(page);
        listDTO.setPageSize(size);
        return  listDTO;
    }

    public Texts findById(Integer id) {
        Optional<Texts> noteResult = textRepository.findById(id);
        return noteResult.get();
    }

    public Texts update(Texts texts) {
        Texts textsResult = textRepository.save(texts);
        return textsResult;
    }

    public Texts patch(Texts texts) {
        Integer tid = texts.getTid();
        Texts oldTexts = this.findById(tid);
        Texts newTexts = new Texts();
        BeanUtils.copyProperties(oldTexts, newTexts);
        BeanUtils.copyProperties(texts, newTexts);
        Texts textsResult = textRepository.save(newTexts);
        return textsResult;
    }

    public Texts delete(Integer id) {
        Texts texts = new Texts();
        texts.setIsDelete(1);
        texts.setTid(id);
        Texts textsResult = textRepository.save(texts);
        return textsResult;
    }
    public ListDTO<TextResponse> findSelfList (String uid, String search , Integer page, Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page list =  textRepository.findByTitleContaining(search,pageable);
        ListDTO<TextResponse> resDTO = new ListDTO<>();
        resDTO.setList(list.toList());
        resDTO.setPage(page);
        resDTO.setPageSize(size);
        Texts texts = new Texts();
        texts.setCreateBy(uid);
        Long count  = textRepository.count(Example.of(texts));
        resDTO.setCount(count);
        return  resDTO;
    }

}
