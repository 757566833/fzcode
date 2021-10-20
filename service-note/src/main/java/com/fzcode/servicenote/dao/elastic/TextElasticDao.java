package com.fzcode.servicenote.dao.elastic;

import com.fzcode.servicenote.dto.elastic.TextDTO.TextESCreateDTO;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESDTO;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESUpdateDTO;
import com.fzcode.servicenote.entity.NoteEntity;
import com.fzcode.servicenote.exception.CustomizeException;
import com.fzcode.servicenote.repositroy.NoteRepository;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESPatchDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TextElasticDao {

    private NoteRepository noteRepository;
    @Autowired
    public void setNoteRepository(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public String create(TextESCreateDTO textESCreateDTO) throws CustomizeException {

        NoteEntity noteEntity = new NoteEntity();
        BeanUtils.copyProperties(textESCreateDTO, noteEntity);
        NoteEntity s =  noteRepository.save(noteEntity);
        return s.getId();
    }

    public String delete(String id) throws CustomizeException {
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setId(id);
//        noteRepository.(noteEntity);
//        return  "success";
//        try {
//            client = ElasticBuilder.createElasticClient();
//        } catch (Exception e) {
//            throw new CustomizeException("打开io流出了问题");
//        }
//
//        TextESDTO textESDTO = new TextESDTO();
//        textESDTO.setId(id);
//        textESDTO.setIsDelete(true);
//        UpdateRequest updateRequest = new UpdateRequest(index, id).doc(JSONUtils.stringify(textESDTO), XContentType.JSON);
//        UpdateResponse updateResponse;
//        try {
//            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            throw new CustomizeException("更新出了问题");
//        }
//        try {
//            client.close();
//        } catch (IOException e) {
//            throw new CustomizeException("关闭io流出了问题");
//        }
        // TODO: 2021/10/20 没找到修改的方法
        return id;
    }

    public String update(TextESUpdateDTO textESUpdateDTO) throws CustomizeException {
//        RestHighLevelClient client = null;
//        try {
//            client = ElasticBuilder.createElasticClient();
//        } catch (IOException e) {
//            throw new CustomizeException("打开io流出了问题");
//        }
//        // 如果没有就插入 在后面加  upsert
//        UpdateRequest updateRequest = new UpdateRequest(index, textESUpdateDTO.getId().toString()).doc(JSONUtils.stringify(textESUpdateDTO), XContentType.JSON);
//
//        UpdateResponse updateResponse;
//        try {
//            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            throw new CustomizeException("更新出了问题");
//        }
//        try {
//            client.close();
//        } catch (IOException e) {
//            throw new CustomizeException("关闭io流出了问题");
//        }
        // TODO: 2021/10/20 没找到修改的方法
        return textESUpdateDTO.getId();
    }
    public String patch(TextESPatchDTO textESPatchDTO) throws CustomizeException {
//        RestHighLevelClient client = null;
//        try {
//            client = ElasticBuilder.createElasticClient();
//        } catch (IOException e) {
//            throw new CustomizeException("打开io流出了问题");
//        }
//
//        // 如果没有就插入 在后面加  upsert
//        UpdateRequest updateRequest = new UpdateRequest(index, textESPatchDTO.getId().toString()).doc(JSONUtils.stringify(textESPatchDTO), XContentType.JSON);
//
//        UpdateResponse updateResponse;
//        try {
//            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            throw new CustomizeException("更新出了问题");
//        }
//        try {
//            client.close();
//        } catch (IOException e) {
//            throw new CustomizeException("关闭io流出了问题");
//        }
        // TODO: 2021/10/20 没找到修改的方法
        return textESPatchDTO.getId();
    }
    public String getById(String id) throws CustomizeException {
        Optional<NoteEntity> a =   noteRepository.findById(id);
        return  a.get().getText();
    }

    public ArrayList<TextESDTO> getList(String text) throws CustomizeException {
//        noteRepository.searchSimilar()
// TODO: 2021/10/20  搜索方法
        return new ArrayList<>();

    }
}
