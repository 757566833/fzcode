package com.fzcode.elasticservice.flow;

import com.alibaba.fastjson.JSON;
import com.fzcode.elasticservice.dto.elastic.noteDTO.NoteESCreateDTO;
import com.fzcode.elasticservice.dto.elastic.noteDTO.NoteESDTO;
import com.fzcode.elasticservice.dto.elastic.noteDTO.NoteESPatchDTO;
import com.fzcode.elasticservice.dto.elastic.noteDTO.NoteESUpdateDTO;
import com.fzcode.elasticservice.dto.request.*;
import com.fzcode.elasticservice.dto.response.NoteResDTO;
import com.fzcode.elasticservice.entity.Notes;
import com.fzcode.elasticservice.exception.CustomizeException;
import com.fzcode.elasticservice.service.DB.NoteDBService;
import com.fzcode.elasticservice.service.elastic.NoteElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NoteFlow {
    NoteElasticService noteElasticService;

    @Autowired
    public void setNoteElasticService(NoteElasticService noteElasticService) {
        this.noteElasticService = noteElasticService;
    }

    NoteDBService noteDBService;

    @Autowired
    public void setNoteDBService(NoteDBService noteDBService) {
        this.noteDBService = noteDBService;
    }

    @Transactional(rollbackFor = Exception.class)
    public String create(NoteReqCreateDTO noteReqCreateDTO) throws CustomizeException {
        Notes notes = new Notes();
        notes.setTitle(noteReqCreateDTO.getTitle());
        notes.setDescription(noteReqCreateDTO.getDescription());

        Notes saveResult = noteDBService.save(notes);

        Integer nid = saveResult.getNid();
        NoteESCreateDTO noteESCreateDTO = new NoteESCreateDTO(
                nid.toString(),
                noteReqCreateDTO.getTitle()
        );
        if (noteReqCreateDTO.getSubTitle() != null) {
            noteESCreateDTO.setSubTitle(noteReqCreateDTO.getSubTitle());
        }
        if (noteReqCreateDTO.getTags() != null) {
            noteESCreateDTO.setTags(noteReqCreateDTO.getTags());
        }
        if (noteReqCreateDTO.getText() != null) {
            noteESCreateDTO.setText(noteReqCreateDTO.getText());
        }

        return noteElasticService.create(noteESCreateDTO);
    }

    public Page<Notes> findAll(NoteReqGetListDTO noteReqGetListDTO) {
        return noteDBService.findAll(noteReqGetListDTO.getPage(), noteReqGetListDTO.getSize());
    }

    @Transactional(rollbackFor = Exception.class)
    public String update(NoteReqUpdateDTO noteReqUpdateDTO) throws CustomizeException {
        if (noteReqUpdateDTO.getDescription() != null) {
            Notes notes = new Notes();
            notes.setNid(noteReqUpdateDTO.getNid());
            notes.setTitle(noteReqUpdateDTO.getTitle());
            notes.setDescription(noteReqUpdateDTO.getDescription());
            noteDBService.update(notes);

        }
        NoteESUpdateDTO noteESUpdateDTO = new NoteESUpdateDTO(noteReqUpdateDTO.getNid().toString(), noteReqUpdateDTO.getTitle());
        noteESUpdateDTO.setSubTitle(noteReqUpdateDTO.getSubTitle());
        noteESUpdateDTO.setTags(noteReqUpdateDTO.getTags());
        noteESUpdateDTO.setText(noteReqUpdateDTO.getText());
        return noteElasticService.update(noteESUpdateDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public String patch(NoteReqPatchDTO noteReqPatchDTO) throws CustomizeException {
        if (noteReqPatchDTO.getDescription() != null || noteReqPatchDTO.getTitle() != null) {
            Notes notes = new Notes();
            notes.setNid(noteReqPatchDTO.getNid());
            if (noteReqPatchDTO.getTitle() != null) {
                notes.setTitle(noteReqPatchDTO.getTitle());
            }
            if (noteReqPatchDTO.getDescription() != null) {
                notes.setDescription(noteReqPatchDTO.getDescription());
            }
            noteDBService.patch(notes);
        }
        NoteESPatchDTO noteESPatchDTO = new NoteESPatchDTO(noteReqPatchDTO.getNid().toString());
        noteESPatchDTO.setSubTitle(noteReqPatchDTO.getSubTitle());
        noteESPatchDTO.setTags(noteReqPatchDTO.getTags());
        noteESPatchDTO.setText(noteReqPatchDTO.getText());
        noteESPatchDTO.setTitle(noteReqPatchDTO.getTitle());
        return noteElasticService.patch(noteESPatchDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    public String delete(NoteReqDeleteDTO noteReqDeleteDTO) throws CustomizeException {
        noteDBService.delete(noteReqDeleteDTO.getNid());
        return noteElasticService.delete(noteReqDeleteDTO.getNid().toString());
    }

    public NoteResDTO findById(Integer nid) throws CustomizeException {
        Notes notes = noteDBService.findById(nid);
        String sourceString = noteElasticService.getById(nid.toString());
        NoteESDTO noteESDTO = JSON.parseObject(sourceString, NoteESDTO.class);
        NoteResDTO noteResDTO = new NoteResDTO(
                nid,
                notes.getTitle(),
                notes.getDescription(),
                noteESDTO.getSubTitle(),
                noteESDTO.getText(),
                noteESDTO.getTags()
        );
        return noteResDTO;
    }
}
