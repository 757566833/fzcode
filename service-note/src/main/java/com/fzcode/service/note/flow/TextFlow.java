package com.fzcode.service.note.flow;

import com.fzcode.service.note.dto.elastic.TextDTO.TextESCreateDTO;
import com.fzcode.service.note.dto.elastic.TextDTO.TextESPatchDTO;
import com.fzcode.service.note.dto.elastic.TextDTO.TextESUpdateDTO;
import com.fzcode.service.note.dto.request.Text.TextReqCreateDTO;
import com.fzcode.service.note.dto.request.Text.TextReqPatchDTO;
import com.fzcode.service.note.dto.request.Text.TextReqUpdateDTO;
import com.fzcode.service.note.dto.response.IndexTextListResDTO;
import com.fzcode.service.note.dto.response.ListResDTO;
import com.fzcode.service.note.entity.CidTid;
import com.fzcode.service.note.entity.Texts;
import com.fzcode.service.note.exception.CustomizeException;
import com.fzcode.service.note.repositroy.dbInterface.text.TextDBGetById;
import com.fzcode.service.note.service.DB.CidTidService;
import com.fzcode.service.note.service.DB.TextDBService;
import com.fzcode.service.note.service.elastic.TextElasticService;
import com.fzcode.service.note.utils.HtmlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class TextFlow {
    TextElasticService textElasticService;

    @Autowired
    public void setNoteElasticService(TextElasticService textElasticService) {
        this.textElasticService = textElasticService;
    }

    TextDBService textDBService;

    @Autowired
    public void setTextDBService(TextDBService textDBService) {
        this.textDBService = textDBService;
    }

    CidTidService cidTidService;

    @Autowired
    public void setCidTidService(CidTidService cidTidService) {
        this.cidTidService = cidTidService;
    }

    @Transactional(rollbackFor = Exception.class)
    public String create(TextReqCreateDTO textReqCreateDTO, Integer create_by) throws CustomizeException {
        Texts texts = new Texts();
        BeanUtils.copyProperties(textReqCreateDTO, texts);
        texts.setCreateBy(create_by);

        // 存正文
        Texts saveResult;
        try {
            saveResult = textDBService.save(texts);
        } catch (Exception e) {
            throw new CustomizeException("db存储失败");
        }
        List<CidTid> cidTidList = new ArrayList<CidTid>();
        List<Integer> stringList = textReqCreateDTO.getCategories();
        for (Integer cid:stringList) {
            CidTid cidTid = new CidTid();
            cidTid.setCid(cid);
            cidTid.setTid(saveResult.getTid());
            cidTidList.add(cidTid);
        }
        // 存分类
        try {
             cidTidService.saveAll(cidTidList);
        }catch (Exception e) {
            throw new CustomizeException("db存储失败");
        }
        Integer tid = saveResult.getTid();
        TextESCreateDTO textESCreateDTO = new TextESCreateDTO(
                tid.toString(),
                textReqCreateDTO.getTitle()
        );

        textESCreateDTO.setCategories(textReqCreateDTO.getCategories());
        textESCreateDTO.setText(HtmlUtils.html2Text(textReqCreateDTO.getHtml()));

        return textElasticService.create(textESCreateDTO);
    }

    public ListResDTO<IndexTextListResDTO> findAll(Integer page, Integer size) {
        return textDBService.findAll(page, size);
    }

    @Transactional(rollbackFor = Exception.class)
    public String update(Integer id, TextReqUpdateDTO textReqUpdateDTO) throws CustomizeException {
        if (textReqUpdateDTO.getDescription() != null) {
            Texts texts = new Texts();
            texts.setTid(id);
            texts.setTitle(textReqUpdateDTO.getTitle());
            texts.setDescription(textReqUpdateDTO.getDescription());
//            texts.setText(textReqUpdateDTO.getText());
            textDBService.update(texts);
        }
        TextESUpdateDTO textESUpdateDTO = new TextESUpdateDTO(id.toString(), textReqUpdateDTO.getTitle());
//        textESUpdateDTO.setSubTitle(textReqUpdateDTO.getSubTitle());
        textESUpdateDTO.setCategories(textReqUpdateDTO.getCategories());

        textESUpdateDTO.setText(HtmlUtils.html2Text(textReqUpdateDTO.getHtml()));
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
//        textESPatchDTO.setSubTitle(textReqPatchDTO.getSubTitle());
        textESPatchDTO.setCategories(textReqPatchDTO.getCategories());
        textESPatchDTO.setText(HtmlUtils.html2Text(textReqPatchDTO.getHtml()));
        textESPatchDTO.setTitle(textReqPatchDTO.getTitle());
        return textElasticService.patch(textESPatchDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    public String delete(Integer nid) throws CustomizeException {
        textDBService.delete(nid);
        return textElasticService.delete(nid.toString());
    }

    public TextDBGetById findById(Integer nid) throws CustomizeException {
        TextDBGetById texts = textDBService.findByIdWithUserInfo(nid);
//        System.out.println(texts.get("tags").toString());
//        List<String> list = JSON.parseArray(texts.get("tags").toString(), String.class);
//
//        Map<String,Object> map = new HashMap<>();
//        BeanUtils.copyProperties(texts,map);
//        map.put("tags",list);
//        List<String> list = JSONArray.parseArray(texts.get("tags").toString(), String.class);


//        String sourceString = textElasticService.getById(nid.toString());
//        TextESDTO textESDTO = JSON.parseObject(sourceString, TextESDTO.class);
//        TextResDTO textResDTO = new TextResDTO(
//                nid,
//                texts.getTitle(),
//                texts.getDescription(),
//                texts.getText(),
//                Arrays.asList(texts.getCategories().split(","))
//
//        );
        return texts;
    }
}
