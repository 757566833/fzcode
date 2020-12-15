package com.fzcode.noteservice.service.DB;

import com.fzcode.noteservice.entity.Columns;
import com.fzcode.noteservice.entity.Texts;
import com.fzcode.noteservice.repositroy.TextRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TextDBService {


    TextRepository textRepository;

    @Autowired
    public void setNoteRepository(TextRepository textRepository) {
        this.textRepository = textRepository;
    }


    public Texts save(Texts texts) {
        Texts textsResult = textRepository.save(texts);
        return textsResult;
    }

    public Page<Texts> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Texts> list = textRepository.findAll(pageable);
        return list;
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
        Texts oldText = this.findById(tid);
        Texts newText = new Texts();
        BeanUtils.copyProperties(oldText,newText);
        BeanUtils.copyProperties(texts,newText);
        Texts textResult = textRepository.save(newText);
        return textResult;
    }

    public Texts delete(Integer id) {
        Texts texts = new Texts();
        texts.setIsDelete(true);
        texts.setTid(id);
        Texts textsResult = textRepository.save(texts);
        return textsResult;
    }
}
