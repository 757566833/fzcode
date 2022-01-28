package com.fzcode.servicenote.service;

import com.fzcode.internalcommon.dto.common.ListDTO;
import com.fzcode.internalcommon.dto.servicenote.request.note.SearchRequest;
import com.fzcode.servicenote.dao.elastic.TextElasticDao;
import com.fzcode.servicenote.entity.Note;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NoteService {
    TextElasticDao textElasticDao;

    @Autowired
    public void setTextElasticDao(TextElasticDao textElasticDao) {
        this.textElasticDao = textElasticDao;
    }

    public ListDTO<Map<String, List<String>>> search (SearchRequest searchRequest)  {
        SearchRequest _searchRequest = new SearchRequest();
        BeanUtils.copyProperties(searchRequest,_searchRequest);
        _searchRequest.setPageSize(10);
        SearchHits<Note> searchHits = textElasticDao.search(_searchRequest);
        List list = searchHits.stream().collect(Collectors.toList());
        ListDTO listDTO = new ListDTO();
        listDTO.setList(list);
        listDTO.setCount(searchHits.getTotalHits());
        listDTO.setPageSize(_searchRequest.getPageSize());
        listDTO.setPage(searchRequest.getPage());

        return listDTO;
    }
}
