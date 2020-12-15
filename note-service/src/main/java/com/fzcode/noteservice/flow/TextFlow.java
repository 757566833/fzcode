package com.fzcode.noteservice.flow;

import com.alibaba.fastjson.JSON;
import com.fzcode.noteservice.dto.elastic.TextDTO.TextESCreateDTO;
import com.fzcode.noteservice.dto.elastic.TextDTO.TextESDTO;
import com.fzcode.noteservice.dto.elastic.TextDTO.TextESPatchDTO;
import com.fzcode.noteservice.dto.elastic.TextDTO.TextESUpdateDTO;
import com.fzcode.noteservice.dto.request.Text.TextReqCreateDTO;
import com.fzcode.noteservice.dto.request.Text.TextReqPatchDTO;
import com.fzcode.noteservice.dto.request.Text.TextReqUpdateDTO;
import com.fzcode.noteservice.dto.response.TextResDTO;
import com.fzcode.noteservice.entity.Texts;
import com.fzcode.noteservice.exception.CustomizeException;
import com.fzcode.noteservice.service.DB.TextDBService;
import com.fzcode.noteservice.service.elastic.TextElasticService;
import com.fzcode.noteservice.utils.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class TextFlow {
    TextElasticService textElasticService;

    @Autowired
    public void setNoteElasticService(TextElasticService textElasticService) {
        this.textElasticService = textElasticService;
    }

    TextDBService textDBService;

    @Autowired
    public void setNoteDBService(TextDBService textDBService) {
        this.textDBService = textDBService;
    }

    @Transactional(rollbackFor = Exception.class)
    public String create(TextReqCreateDTO textReqCreateDTO) throws CustomizeException {
        Texts texts = new Texts();
        BeanUtils.copyProperties(textReqCreateDTO, texts);
        texts.setTags(String.join(",", textReqCreateDTO.getTags()));
        Texts saveResult;
        try {
            saveResult = textDBService.save(texts);
        } catch (Exception e) {
            throw new CustomizeException("db存储失败");
        }
        Integer tid = saveResult.getTid();
        TextESCreateDTO textESCreateDTO = new TextESCreateDTO(
                tid.toString(),
                textReqCreateDTO.getTitle()
        );
        if (textReqCreateDTO.getSubTitle() != null) {
            textESCreateDTO.setSubTitle(textReqCreateDTO.getSubTitle());
        }
        if (textReqCreateDTO.getTags() != null) {
            textESCreateDTO.setTags(textReqCreateDTO.getTags());
        }
        if (textReqCreateDTO.getText() != null) {
            textESCreateDTO.setText(MarkdownUtils.markdown2string(textReqCreateDTO.getText()));
        }

        return textElasticService.create(textESCreateDTO);
    }

    public Page<Texts> findAll(Integer page, Integer size) {
        return textDBService.findAll(page, size);
    }

    @Transactional(rollbackFor = Exception.class)
    public String update(Integer id, TextReqUpdateDTO textReqUpdateDTO) throws CustomizeException {
        if (textReqUpdateDTO.getDescription() != null) {
            Texts texts = new Texts();
            texts.setTid(id);
            texts.setTitle(textReqUpdateDTO.getTitle());
            texts.setDescription(textReqUpdateDTO.getDescription());
            texts.setText(textReqUpdateDTO.getText());
            textDBService.update(texts);
        }
        TextESUpdateDTO textESUpdateDTO = new TextESUpdateDTO(id.toString(), textReqUpdateDTO.getTitle());
        textESUpdateDTO.setSubTitle(textReqUpdateDTO.getSubTitle());
        textESUpdateDTO.setTags(textReqUpdateDTO.getTags());
        textESUpdateDTO.setText(MarkdownUtils.markdown2string(textReqUpdateDTO.getText()));
        return textElasticService.update(textESUpdateDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public String patch(Integer nid, TextReqPatchDTO textReqPatchDTO) throws CustomizeException {
        if (textReqPatchDTO.getDescription() != null || textReqPatchDTO.getTitle() != null) {
            Texts texts = new Texts();
            texts.setTid(nid);
            if (textReqPatchDTO.getTitle() != null) {
                texts.setTitle(textReqPatchDTO.getTitle());
            }
            if (textReqPatchDTO.getDescription() != null) {
                texts.setDescription(textReqPatchDTO.getDescription());
            }
            textDBService.patch(texts);
        }
        TextESPatchDTO textESPatchDTO = new TextESPatchDTO(nid.toString());
        textESPatchDTO.setSubTitle(textReqPatchDTO.getSubTitle());
        textESPatchDTO.setTags(textReqPatchDTO.getTags());
        textESPatchDTO.setText(MarkdownUtils.markdown2string(textReqPatchDTO.getText()));
        textESPatchDTO.setTitle(textReqPatchDTO.getTitle());
        return textElasticService.patch(textESPatchDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    public String delete(Integer nid) throws CustomizeException {
        textDBService.delete(nid);
        return textElasticService.delete(nid.toString());
    }

    public TextResDTO findById(Integer nid) throws CustomizeException {
        Texts texts = textDBService.findById(nid);
//        String sourceString = textElasticService.getById(nid.toString());
//        TextESDTO textESDTO = JSON.parseObject(sourceString, TextESDTO.class);
        TextResDTO textResDTO = new TextResDTO(
                nid,
                texts.getTitle(),
                texts.getDescription(),
                texts.getText(),
                Arrays.asList(texts.getTags().split(","))

        );
        return textResDTO;
    }
}
