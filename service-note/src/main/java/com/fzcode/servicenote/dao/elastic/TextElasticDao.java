package com.fzcode.servicenote.dao.elastic;

import com.fzcode.internalcommon.dto.servicenote.request.note.SearchRequest;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESCreateDTO;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESUpdateDTO;
import com.fzcode.servicenote.entity.Note;
import com.fzcode.servicenote.repositroy.NoteRepository;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESPatchDTO;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TextElasticDao {

    private NoteRepository noteRepository;
    @Autowired
    public void setNoteRepository(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    public void  setElasticsearchRestTemplate(ElasticsearchRestTemplate elasticsearchRestTemplate){
        this.elasticsearchRestTemplate= elasticsearchRestTemplate;
    }

    public String create(TextESCreateDTO textESCreateDTO) throws CustomizeException {

        Note note = new Note();
        BeanUtils.copyProperties(textESCreateDTO, note);
        Note s =  noteRepository.save(note);
        return s.getId();
    }

    public String delete(String id) throws CustomizeException {
        Note note = new Note();
        note.setId(id);
//        noteRepository.(noteEntity);
//        return  "success";
//        try {
//            client = ElasticBuilder.createElasticClient();
//        } catch (Exception e) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"打开io流出了问题");
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
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"更新出了问题");
//        }
//        try {
//            client.close();
//        } catch (IOException e) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"关闭io流出了问题");
//        }
        // TODO: 2021/10/20 没找到修改的方法
        return id;
    }

    public String update(TextESUpdateDTO textESUpdateDTO) throws CustomizeException {
//        RestHighLevelClient client = null;
//        try {
//            client = ElasticBuilder.createElasticClient();
//        } catch (IOException e) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"打开io流出了问题");
//        }
//        // 如果没有就插入 在后面加  upsert
//        UpdateRequest updateRequest = new UpdateRequest(index, textESUpdateDTO.getId().toString()).doc(JSONUtils.stringify(textESUpdateDTO), XContentType.JSON);
//
//        UpdateResponse updateResponse;
//        try {
//            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"更新出了问题");
//        }
//        try {
//            client.close();
//        } catch (IOException e) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"关闭io流出了问题");
//        }
        // TODO: 2021/10/20 没找到修改的方法
        return textESUpdateDTO.getId();
    }
    public String patch(TextESPatchDTO textESPatchDTO) throws CustomizeException {
//        RestHighLevelClient client = null;
//        try {
//            client = ElasticBuilder.createElasticClient();
//        } catch (IOException e) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"打开io流出了问题");
//        }
//
//        // 如果没有就插入 在后面加  upsert
//        UpdateRequest updateRequest = new UpdateRequest(index, textESPatchDTO.getId().toString()).doc(JSONUtils.stringify(textESPatchDTO), XContentType.JSON);
//
//        UpdateResponse updateResponse;
//        try {
//            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"更新出了问题");
//        }
//        try {
//            client.close();
//        } catch (IOException e) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"关闭io流出了问题");
//        }
        // TODO: 2021/10/20 没找到修改的方法
        return textESPatchDTO.getId();
    }
    public String getById(String id) throws CustomizeException {
        Optional<Note> a =   noteRepository.findById(id);
        return  a.get().getText();
    }

    public SearchHits<Note> search(SearchRequest searchRequest) {
//        noteRepository.searchSimilar()
// TODO: 2021/10/20  搜索方法
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 分页参数
        Pageable pageable = PageRequest.of(searchRequest.getPage()-1, searchRequest.getPageSize());
        // 高亮参数
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        String preTags = "<span class='red'>";
        String postTags = "</span>";
        highlightBuilder.preTags(preTags);
        highlightBuilder.postTags(postTags);
        highlightBuilder.field("title");
        highlightBuilder.field("text");
        // 搜索参数
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(searchRequest.getSearch(),"text","title").type(MultiMatchQueryBuilder.Type.MOST_FIELDS);
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.withHighlightBuilder(highlightBuilder).withQuery(queryBuilder).withPageable(pageable).build();
        SearchHits<Note> noteSearchHits = elasticsearchRestTemplate.search(nativeSearchQuery,Note.class, IndexCoordinates.of("blog-note"));

        return  noteSearchHits;

    }
}
