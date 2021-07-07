package com.fzcode.servicenote.dao.DB;

import com.alibaba.fastjson.JSON;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.servicenote.response.text.TextResponse;
import com.fzcode.servicenote.repositroy.CidTidRepository;
import com.fzcode.servicenote.repositroy.mapper.CidTidMapper;
import com.fzcode.servicenote.repositroy.mapper.TextDBFindListMapper;
import com.fzcode.servicenote.repositroy.mapper.TextDBGetByIdMapper;
import com.fzcode.servicenote.entity.Texts;
import com.fzcode.servicenote.exception.CustomizeException;
import com.fzcode.servicenote.repositroy.TextRepository;
import com.fzcode.servicenote.utils.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Texts save(Texts texts) {
        Texts textsResult = textRepository.save(texts);
        return textsResult;
    }

    public ListResponseDTO<TextResponse> findAll(int page, int size) {
        ListResponseDTO<TextResponse> resDTO = new ListResponseDTO<>();

        List<TextDBFindListMapper> list = textRepository.findList(size,(page-1)*size);
        String tidString ="";
        for (TextDBFindListMapper t:list) {
            tidString+=t.getTid();
            tidString+=",";
        }
        List<CidTidMapper> list2 = new ArrayList<>();
        if(tidString.length()>=2){
            tidString = tidString.substring(0,tidString.length()-2);
            list2 = cidTidRepository.findList(tidString);
        }


        HashMap<Integer,String> hashMap = new HashMap<>();
        for (CidTidMapper t:list2) {
            hashMap.put(t.getTid(),t.getCidList());
        }
        Integer count = textRepository.findListCount(size,(page-1)*size).get(0).getCount();
        resDTO.setList(new ArrayList<>());
        List<TextResponse> resList = resDTO.getList();
        ListUtils.copyList(list,resList, TextResponse.class);
        for (TextResponse t:resList) {
            String arrStr =hashMap.get(t.getTid());
            if(arrStr!=null){
                String[] arr =  arrStr.split(",");
                List<Integer> numArr = new ArrayList<>();
                for (String s: arr) {
                    numArr.add(Integer.parseInt(s));
                }
                t.setCategories(numArr);
            }

        }
        resDTO.setCount(count);
        resDTO.setPage(page);
        resDTO.setPageSize(size);
        return resDTO;
    }

    public Texts findById(Integer id) {
        Optional<Texts> noteResult = textRepository.findById(id);
        return noteResult.get();
    }

    public TextDBGetByIdMapper findByIdWithUserInfo(Integer id) throws CustomizeException {
        List<TextDBGetByIdMapper> noteDBList = textRepository.findByIdWithUserInfo(id);
        if (noteDBList.size() == 0) {
            throw new CustomizeException("不存在");
        }
        System.out.println(JSON.toJSONString(noteDBList.get(0)));
        return noteDBList.get(0);
    }

    public Texts update(Texts texts) {
        Texts textsResult = textRepository.save(texts);
        return textsResult;
    }

    public Texts patch(Texts texts) {
        Integer tid = texts.getTid();
        Texts oldText = this.findById(tid);
        Texts newText = new Texts();
        BeanUtils.copyProperties(oldText, newText);
        BeanUtils.copyProperties(texts, newText);
        Texts textResult = textRepository.save(newText);
        return textResult;
    }

    public Texts delete(Integer id) {
        Texts texts = new Texts();
        texts.setIsDelete(1);
        texts.setTid(id);
        Texts textsResult = textRepository.save(texts);
        return textsResult;
    }
}
