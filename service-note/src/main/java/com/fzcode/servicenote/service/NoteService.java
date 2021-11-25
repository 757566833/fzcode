package com.fzcode.servicenote.service;

import com.fzcode.internalcommon.dto.common.ListRequestDTO;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.servicenote.request.note.SearchRequest;
import com.fzcode.servicenote.dao.elastic.TextElasticDao;
import com.fzcode.servicenote.entity.Note;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteService {
    TextElasticDao textElasticDao;

    @Autowired
    public void setTextElasticDao(TextElasticDao textElasticDao) {
        this.textElasticDao = textElasticDao;
    }

    public ListResponseDTO<Map<String, List<String>>> search (SearchRequest searchRequest)  {
        SearchRequest _searchRequest = new SearchRequest();
        BeanUtils.copyProperties(searchRequest,_searchRequest);
        SearchHits<Note> searchHits = textElasticDao.search(_searchRequest);

        _searchRequest.setPageSize(10);
        List list = new ArrayList();
//        searchHits.stream().forEach(a-> list.add(a.getContent()));
        searchHits.stream().forEach(a->{
            Map map = new HashMap();
            map.put("highlight",a.getHighlightFields());
            map.put("title",a.getContent().getTitle());
            map.put("id",a.getContent().getId());
            list.add(map);
        });
        ListResponseDTO listResponseDTO = new ListResponseDTO();
        listResponseDTO.setList(list);
        listResponseDTO.setCount((int)searchHits.getTotalHits());
        listResponseDTO.setPageSize(_searchRequest.getPageSize());
        listResponseDTO.setPage(searchRequest.getPage());

        return  listResponseDTO;
    }
}
