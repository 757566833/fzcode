package com.fzcode.servicenote.service;

import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextCreateRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextPatchRequest;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextUpdateRequest;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESCreateDTO;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESPatchDTO;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESUpdateDTO;
import com.fzcode.servicenote.entity.CidTid;
import com.fzcode.servicenote.entity.Texts;
import com.fzcode.servicenote.exception.CustomizeException;
import com.fzcode.servicenote.repositroy.mapper.TextDBGetByIdMapper;
import com.fzcode.servicenote.dao.DB.CidTidDao;
import com.fzcode.servicenote.dao.DB.TextDBDao;
import com.fzcode.servicenote.dao.elastic.TextElasticDao;
import com.fzcode.servicenote.utils.HtmlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TextService {
    TextElasticDao textElasticDao;

    @Autowired
    public void setNoteElasticService(TextElasticDao textElasticDao) {
        this.textElasticDao = textElasticDao;
    }

    TextDBDao textDBDao;

    @Autowired
    public void setTextDBService(TextDBDao textDBDao) {
        this.textDBDao = textDBDao;
    }

    CidTidDao cidTidDao;

    @Autowired
    public void setCidTidService(CidTidDao cidTidDao) {
        this.cidTidDao = cidTidDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public String create(TextCreateRequest textCreateRequest, Integer create_by) throws CustomizeException {
        Texts texts = new Texts();
        BeanUtils.copyProperties(textCreateRequest, texts);
        texts.setCreateBy(create_by);

        // 存正文
        Texts saveResult;
        try {
            saveResult = textDBDao.save(texts);
        } catch (Exception e) {
            throw new CustomizeException("db存储失败");
        }
        List<CidTid> cidTidList = new ArrayList<CidTid>();
        List<Integer> stringList = textCreateRequest.getCategories();
        for (Integer cid:stringList) {
            CidTid cidTid = new CidTid();
            cidTid.setCid(cid);
            cidTid.setTid(saveResult.getTid());
            cidTidList.add(cidTid);
        }
        // 存分类
        try {
             cidTidDao.saveAll(cidTidList);
        }catch (Exception e) {
            throw new CustomizeException("db存储失败");
        }
        Integer tid = saveResult.getTid();
        TextESCreateDTO textESCreateDTO = new TextESCreateDTO(
                tid.toString(),
                textCreateRequest.getTitle()
        );

        textESCreateDTO.setCategories(textCreateRequest.getCategories());
        textESCreateDTO.setText(HtmlUtils.html2Text(textCreateRequest.getHtml()));

        return textElasticDao.create(textESCreateDTO);
    }

    public ListResponseDTO<TextResponse> findAll(Integer page, Integer size) {
        return textDBDao.findAll(page, size);
    }

    @Transactional(rollbackFor = Exception.class)
    public String update(Integer id, TextUpdateRequest textUpdateRequest) throws CustomizeException {
        if (textUpdateRequest.getDescription() != null) {
            Texts texts = new Texts();
            texts.setTid(id);
            texts.setTitle(textUpdateRequest.getTitle());
            texts.setDescription(textUpdateRequest.getDescription());
//            texts.setText(textReqUpdateDTO.getText());
            textDBDao.update(texts);
        }
        TextESUpdateDTO textESUpdateDTO = new TextESUpdateDTO(id.toString(), textUpdateRequest.getTitle());
//        textESUpdateDTO.setSubTitle(textReqUpdateDTO.getSubTitle());
        textESUpdateDTO.setCategories(textUpdateRequest.getCategories());

        textESUpdateDTO.setText(HtmlUtils.html2Text(textUpdateRequest.getHtml()));
        return textElasticDao.update(textESUpdateDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public String patch(Integer nid, TextPatchRequest textPatchRequest) throws CustomizeException {
        if (textPatchRequest.getDescription() != null || textPatchRequest.getTitle() != null) {
            Texts texts = new Texts();
            texts.setTid(nid);
            if (textPatchRequest.getTitle() != null) {
                texts.setTitle(textPatchRequest.getTitle());
            }
            if (textPatchRequest.getDescription() != null) {
                texts.setDescription(textPatchRequest.getDescription());
            }
            textDBDao.patch(texts);
        }
        TextESPatchDTO textESPatchDTO = new TextESPatchDTO(nid.toString());
//        textESPatchDTO.setSubTitle(textReqPatchDTO.getSubTitle());
        textESPatchDTO.setCategories(textPatchRequest.getCategories());
        textESPatchDTO.setText(HtmlUtils.html2Text(textPatchRequest.getHtml()));
        textESPatchDTO.setTitle(textPatchRequest.getTitle());
        return textElasticDao.patch(textESPatchDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    public String delete(Integer nid) throws CustomizeException {
        textDBDao.delete(nid);
        return textElasticDao.delete(nid.toString());
    }

    public TextDBGetByIdMapper findById(Integer nid) throws CustomizeException {
        TextDBGetByIdMapper texts = textDBDao.findByIdWithUserInfo(nid);
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
