package com.fzcode.servicenote.service;

import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextRequest;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.utils.JSONUtils;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESCreateDTO;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESPatchDTO;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESUpdateDTO;
import com.fzcode.servicenote.entity.CidTid;
import com.fzcode.servicenote.entity.Texts;
import com.fzcode.servicenote.repositroy.mapper.TextDBGetByIdMapper;
import com.fzcode.servicenote.dao.DB.CidTidDao;
import com.fzcode.servicenote.dao.DB.TextDBDao;
import com.fzcode.servicenote.dao.elastic.TextElasticDao;
import com.fzcode.servicenote.utils.HtmlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TextService {
    TextElasticDao textElasticDao;

    @Autowired
    public void setTextElasticDao(TextElasticDao textElasticDao) {
        this.textElasticDao = textElasticDao;
    }

    TextDBDao textDBDao;

    @Autowired
    public void setTextDBDao(TextDBDao textDBDao) {
        this.textDBDao = textDBDao;
    }

    CidTidDao cidTidDao;

    @Autowired
    public void setCidTidDao(CidTidDao cidTidDao) {
        this.cidTidDao = cidTidDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer create(TextRequest textRequest, Integer create_by) throws CustomizeException {
        System.out.println("createBy:"+create_by);
        Texts texts = new Texts();
        System.out.println("before copy");
        BeanUtils.copyProperties(textRequest, texts);
        System.out.println("after copy");
        String content = HtmlUtils.html2Text(textRequest.getHtml());
        texts.setCreateBy(create_by);

        // 存正文
        Texts saveResult;
        try {
            saveResult = textDBDao.save(texts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"正文db存储失败");
        }
        List<CidTid> cidTidList = new ArrayList<CidTid>();
        List<Integer> stringList = textRequest.getCategories();
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
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"分类db存储失败");
        }
        Integer tid = saveResult.getTid();
        TextESCreateDTO textESCreateDTO = new TextESCreateDTO(
                tid,
                textRequest.getTitle()
        );

        textESCreateDTO.setCategories(textRequest.getCategories());
        textESCreateDTO.setContent(content);
        System.out.println("createBy2:"+saveResult.getCreateBy());
        textESCreateDTO.setCreateBy(saveResult.getCreateBy());
        textESCreateDTO.setCreateTime(saveResult.getCreateTime());
        textESCreateDTO.setUpdateTime(saveResult.getUpdateTime());
        System.out.println("json:"+JSONUtils.stringify(textESCreateDTO));
        if(content!=null){
            if (content.length() > 50) {
                textESCreateDTO.setSummary(content.substring(0, 50));
            } else {
                textESCreateDTO.setSummary(content);
            }
        }
        return textElasticDao.create(textESCreateDTO);
    }

    public ListResponseDTO<TextResponse> findAll(Integer page, Integer size) {
        return textDBDao.findAll(page, size);
    }

    @Transactional(rollbackFor = Exception.class)
    public String update(Integer id, TextRequest textRequest) throws CustomizeException {
        if (textRequest.getDescription() != null) {
            Texts texts = new Texts();
            texts.setTid(id);
            texts.setTitle(textRequest.getTitle());
            texts.setDescription(textRequest.getDescription());
//            texts.setText(textReqUpdateDTO.getText());
            textDBDao.update(texts);
        }
        TextESUpdateDTO textESUpdateDTO = new TextESUpdateDTO(id.toString(), textRequest.getTitle());
//        textESUpdateDTO.setSubTitle(textReqUpdateDTO.getSubTitle());
        textESUpdateDTO.setCategories(textRequest.getCategories());

        textESUpdateDTO.setText(HtmlUtils.html2Text(textRequest.getHtml()));
        return textElasticDao.update(textESUpdateDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public String patch(Integer nid,  TextRequest textRequest) throws CustomizeException {
        if (textRequest.getDescription() != null || textRequest.getTitle() != null) {
            Texts texts = new Texts();
            texts.setTid(nid);
            if (textRequest.getTitle() != null) {
                texts.setTitle(textRequest.getTitle());
            }
            if (textRequest.getDescription() != null) {
                texts.setDescription(textRequest.getDescription());
            }
            textDBDao.patch(texts);
        }
        TextESPatchDTO textESPatchDTO = new TextESPatchDTO(nid.toString());
        textESPatchDTO.setCategories(textRequest.getCategories());
        textESPatchDTO.setText(HtmlUtils.html2Text(textRequest.getHtml()));
        textESPatchDTO.setTitle(textRequest.getTitle());
        return textElasticDao.patch(textESPatchDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Integer nid) throws CustomizeException {
        textDBDao.delete(nid);
        return textElasticDao.delete(nid);
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
