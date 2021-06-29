package com.fzcode.service.note.service.DB;

import com.alibaba.fastjson.JSON;
import com.fzcode.service.note.dto.response.IndexTextListResDTO;
import com.fzcode.service.note.dto.response.ListResDTO;
import com.fzcode.service.note.repositroy.CidTidRepository;
import com.fzcode.service.note.repositroy.dbInterface.cidTid.CidTidList;
import com.fzcode.service.note.repositroy.dbInterface.text.TextDBFindList;
import com.fzcode.service.note.repositroy.dbInterface.text.TextDBGetById;
import com.fzcode.service.note.entity.Texts;
import com.fzcode.service.note.exception.CustomizeException;
import com.fzcode.service.note.repositroy.TextRepository;
import com.fzcode.service.note.utils.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TextDBService {


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

    public ListResDTO<IndexTextListResDTO> findAll(int page, int size) {
        ListResDTO<IndexTextListResDTO> resDTO = new ListResDTO<IndexTextListResDTO>();

        List<TextDBFindList> list = textRepository.findList(size,(page-1)*size);
        String tidString ="";
        for (TextDBFindList t:list) {
            tidString+=t.getTid();
            tidString+=",";
        }
        List<CidTidList> list2 = new ArrayList<>();
        if(tidString.length()>=2){
            tidString = tidString.substring(0,tidString.length()-2);
            list2 = cidTidRepository.findList(tidString);
        }


        HashMap<Integer,String> hashMap = new HashMap<>();
        for (CidTidList t:list2) {
            hashMap.put(t.getTid(),t.getCidList());
        }
        Integer count = textRepository.findListCount(size,(page-1)*size).get(0).getCount();
        resDTO.setList(new ArrayList<>());
        List<IndexTextListResDTO> resList = resDTO.getList();
        ListUtils.copyList(list,resList,IndexTextListResDTO.class);
        for (IndexTextListResDTO t:resList) {
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
        resDTO.setSize(size);
        return resDTO;
    }

    public Texts findById(Integer id) {
        Optional<Texts> noteResult = textRepository.findById(id);
        return noteResult.get();
    }

    public TextDBGetById findByIdWithUserInfo(Integer id) throws CustomizeException {
        List<TextDBGetById> noteDBList = textRepository.findByIdWithUserInfo(id);
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
