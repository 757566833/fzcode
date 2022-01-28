package com.fzcode.servicenote.service;

import com.fzcode.internalcommon.crud.Create;
import com.fzcode.internalcommon.crud.FullUpdate;
import com.fzcode.internalcommon.crud.IncrementalUpdate;
import com.fzcode.internalcommon.dto.common.BatchGetDTO;
import com.fzcode.internalcommon.dto.common.ListDTO;
import com.fzcode.internalcommon.dto.common.Users;
import com.fzcode.internalcommon.dto.servicenote.es.TextESDTO;
import com.fzcode.internalcommon.dto.servicenote.request.text.TextRequest;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.http.Http;
import com.fzcode.internalcommon.utils.CopyUtils;
import com.fzcode.internalcommon.utils.JSONUtils;
import com.fzcode.servicenote.config.Services;
import com.fzcode.servicenote.dao.DB.CategoryDBDao;
import com.fzcode.servicenote.entity.Categories;
import com.fzcode.servicenote.entity.CidTid;
import com.fzcode.servicenote.entity.Texts;
import com.fzcode.servicenote.dao.DB.CidTidDao;
import com.fzcode.servicenote.dao.DB.TextDBDao;
import com.fzcode.servicenote.dao.elastic.TextElasticDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;


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

    private Http http;
    @Autowired
    public void setHttp(Http http) {
        this.http = http;
    }

    Services services;
    @Autowired
    public void setServices(Services services){
        this.services = services;
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

    public ListDTO<TextResponse> findAll(Integer page, Integer size) throws CustomizeException {
        /**
         * 内容
         */
        ListDTO<Texts> textList = textDBDao.findByPage(page,size);
        System.out.println("textList");
        System.out.println(JSONUtils.stringify(textList));
        /**
         * 内容列表中提取tid
         */
        HashSet<String> textUidSet = new HashSet<>();
        ArrayList<Integer> textTidList = new ArrayList<>();
        textList.getList().forEach((texts)->{
            textTidList.add(texts.getTid());
            textUidSet.add(texts.getCreateBy());
        });
        /**
         * 根据提取的tid查找分类
         */
        List<CidTid> cidTidList =  cidTidDao.getListByTid(textTidList);
        /**
         * tid cid的 map
         */
        HashMap<Integer,HashSet<Integer>> tidCidMap = new HashMap<>();
        /**
         * tid去重
         */
        HashSet<Integer> textCidSet = new HashSet<>();
        cidTidList.forEach((cidTid)->{
            textCidSet.add(cidTid.getCid());
            if(tidCidMap.get(cidTid.getTid())==null){
                HashSet set = new HashSet();
                set.add(cidTid.getCid());
                tidCidMap.put(cidTid.getTid(),set);
            }else{
                tidCidMap.get(cidTid.getTid()).add(cidTid.getCid());
            }
        });

        String[] arr = textUidSet.toArray(new String[textCidSet.size()]);
        BatchGetDTO params = new BatchGetDTO();
        params.setIds(StringUtils.join(arr,","));
        Users[] userList =http.get(services.getService().getAuth().getHost()+"/user/get/list", params,Users[].class);
        HashMap<String,Users> userMap = new HashMap();
        for (int i = 0; i < userList.length; i++) {
            Users user  = userList[i];
            System.out.println(user);
            System.out.println(JSONUtils.stringify(user));
            System.out.println(user.getClass().getName());
            userMap.put(user.getUid(),user);
        }
        ListDTO<TextResponse> listDTO = new ListDTO();
        List<TextResponse> textResponseList = new ArrayList<>();
        textList.getList().forEach((texts)->{
            TextResponse textResponse = new TextResponse();
            CopyUtils.copyProperties(userMap.get(texts.getCreateBy()),textResponse);
            CopyUtils.copyProperties(texts,textResponse);
            HashSet<Integer> set = tidCidMap.get(texts.getTid());
            textResponse.setCategories(new ArrayList<>(set));
            textResponseList.add(textResponse);
        });
        listDTO.setList(textResponseList);
        listDTO.setCount(textList.getCount());
        listDTO.setPage(page);
        listDTO.setPageSize(size);
        return listDTO;
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
