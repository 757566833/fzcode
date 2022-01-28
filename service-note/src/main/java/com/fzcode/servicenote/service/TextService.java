package com.fzcode.servicenote.service;

import com.fzcode.internalcommon.crud.Create;
import com.fzcode.internalcommon.crud.FullUpdate;
import com.fzcode.internalcommon.crud.IncrementalUpdate;
import com.fzcode.internalcommon.dto.common.ListDTO;
import com.fzcode.internalcommon.dto.servicenote.es.TextESDTO;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextRequest;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.utils.JSONUtils;
import com.fzcode.servicenote.dao.DB.CategoryDBDao;
import com.fzcode.servicenote.entity.Categories;
import com.fzcode.servicenote.entity.CidTid;
import com.fzcode.servicenote.entity.Texts;
import com.fzcode.servicenote.dao.DB.CidTidDao;
import com.fzcode.servicenote.dao.DB.TextDBDao;
import com.fzcode.servicenote.dao.elastic.TextElasticDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    CategoryDBDao categoryDBDao;

    @Autowired
    public void setCidTidDao(CategoryDBDao categoryDBDao) {
        this.categoryDBDao = categoryDBDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer create(@Validated(value = Create.class) TextRequest textRequest, String uid) throws CustomizeException {
        System.out.println("createBy:"+uid);
        Texts texts = new Texts();
        System.out.println("before copy");
        BeanUtils.copyProperties(textRequest, texts);
        System.out.println("after copy");
        String content = textRequest.getContent();
        texts.setCreateBy(uid);

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
        System.out.println(JSONUtils.stringify(cidTidList));
        try {
             cidTidDao.saveAll(cidTidList);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"分类db存储失败");
        }
        Integer tid = saveResult.getTid();
        TextESDTO textESCreateDTO = new TextESDTO(
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

    // todo
    public ListDTO<Texts> findAll(Integer page, Integer size) {
        /**
         * 内容
         */
        ListDTO<Texts> textList = textDBDao.findByPage(page,size);
        System.out.println("textList");
        System.out.println(JSONUtils.stringify(textList));
        /**
         * 内容列表中提取tid
         */
        ArrayList<Integer> textTidList = new ArrayList<>();
        textList.getList().forEach((texts)->{
            textTidList.add(texts.getTid());
        });
        System.out.println("textTidList");
        System.out.println(JSONUtils.stringify(textTidList));
        /**
         * 根据提取的tid查找分类
         */
        List<CidTid> cidTidList =  cidTidDao.getListByTid(textTidList);
        System.out.println("cidTidList");
        System.out.println(JSONUtils.stringify(cidTidList));
        /**
         * tid cid的 map
         */
        HashMap<Integer,Integer> tidCidMap = new HashMap<>();
        /**
         * tid去重
         */
        HashSet<Integer> textCidSet = new HashSet<>();
        cidTidList.forEach((cidTid)->{
            textCidSet.add(cidTid.getCid());
            tidCidMap.put(cidTid.getTid(),cidTid.getCid());
        });
        System.out.println("textCidSet");
        System.out.println(JSONUtils.stringify(textCidSet));
        System.out.println("tidCidMap");
        System.out.println(JSONUtils.stringify(tidCidMap));
        /**
         * 去重后的cid 查询具体信息
         */
        List<Categories> categoriesList = categoryDBDao.getCategoriesByCidIn(textCidSet);
        System.out.println("categoriesList");
        System.out.println(JSONUtils.stringify(categoriesList));
        /**
         * cid 和category的map
         */
        HashMap<Integer,Categories> cidMap = new HashMap<>();
        categoriesList.forEach((categories)->{
            cidMap.put(categories.getCid(),categories);
        });
        System.out.println("cidMap");
        System.out.println(JSONUtils.stringify(cidMap));

        textList.getList().forEach((texts)->{

        });

        return textList;
    }
    public ListDTO<TextResponse> findSelfAll(String uid , String search , Integer page, Integer size) {
        System.out.println("findSelfAll");
        return textDBDao.findSelfList(uid,search ,page, size);
    }
    @Transactional(rollbackFor = Exception.class)
    public Integer update(Integer id,@Validated(value = FullUpdate.class) TextRequest textRequest) throws CustomizeException {
        if (textRequest.getDescription() != null) {
            Texts texts = new Texts();
            texts.setTid(id);
            texts.setTitle(textRequest.getTitle());
            texts.setDescription(textRequest.getDescription());
//            texts.setText(textReqUpdateDTO.getText());
            textDBDao.update(texts);
        }
        TextESDTO textESUpdateDTO = new TextESDTO(id, textRequest.getTitle());
//        textESUpdateDTO.setSubTitle(textReqUpdateDTO.getSubTitle());
        textESUpdateDTO.setCategories(textRequest.getCategories());

        textESUpdateDTO.setContent(textRequest.getContent());
        return textElasticDao.update(textESUpdateDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer patch(Integer nid, @Validated(value = IncrementalUpdate.class) TextRequest textRequest) throws CustomizeException {
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
        TextESDTO textESPatchDTO = new TextESDTO(nid,textRequest.getTitle());
        textESPatchDTO.setCategories(textRequest.getCategories());
        textESPatchDTO.setContent(textRequest.getContent());
        textESPatchDTO.setTitle(textRequest.getTitle());
        return textElasticDao.patch(textESPatchDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Integer nid) throws CustomizeException {
        textDBDao.delete(nid);
        return textElasticDao.delete(nid);
    }

    public Texts findById(Integer nid) {
        Texts texts = textDBDao.findById(nid);
        return texts;
    }
}
