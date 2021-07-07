package com.fzcode.servicenote.dao.elastic;

import com.alibaba.fastjson.JSON;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESCreateDTO;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESDTO;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESUpdateDTO;
import com.fzcode.servicenote.exception.CustomizeException;
import com.fzcode.servicenote.utils.ElasticBuilder;
import com.fzcode.servicenote.dto.elastic.TextDTO.TextESPatchDTO;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class TextElasticDao {

    @Value("${elastic.index}")
    private String index;

    public String create(TextESCreateDTO textESCreateDTO) throws CustomizeException {
        RestHighLevelClient client = null;
        try {
            client = ElasticBuilder.createElasticClient();
        } catch (Exception e) {
            throw new CustomizeException("打开io流出了问题");
        }
        System.out.println(index);
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(textESCreateDTO.getId());
        indexRequest.source(JSON.toJSONString(textESCreateDTO), XContentType.JSON);
        IndexResponse indexResponse;
        try {
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("新增出了问题");
        }
        try {
            client.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("关闭io流出了问题");
        }
        return indexResponse.getId();
    }

    public String delete(String id) throws CustomizeException {
        RestHighLevelClient client = null;
        try {
            client = ElasticBuilder.createElasticClient();
        } catch (Exception e) {
            throw new CustomizeException("打开io流出了问题");
        }
//        DeleteRequest deleteRequest = new DeleteRequest(index, id);
//        DeleteResponse deleteResponse;
//        try {
//            deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            throw new CustomizeException("删除出了问题");
//        }
//        try {
//            client.close();
//        } catch (IOException e) {
//            throw new CustomizeException("关闭io流出了问题");
//        }

        TextESDTO textESDTO = new TextESDTO();
        textESDTO.setId(id);
        textESDTO.setIsDelete(true);
        UpdateRequest updateRequest = new UpdateRequest(index, id).doc(JSON.toJSONString(textESDTO), XContentType.JSON);
        UpdateResponse updateResponse;
        try {
            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CustomizeException("更新出了问题");
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new CustomizeException("关闭io流出了问题");
        }
        return updateResponse.getId();
    }

    public String update(TextESUpdateDTO textESUpdateDTO) throws CustomizeException {
        RestHighLevelClient client = null;
        try {
            client = ElasticBuilder.createElasticClient();
        } catch (IOException e) {
            throw new CustomizeException("打开io流出了问题");
        }
        // 如果没有就插入 在后面加  upsert
        UpdateRequest updateRequest = new UpdateRequest(index, textESUpdateDTO.getId().toString()).doc(JSON.toJSONString(textESUpdateDTO), XContentType.JSON);

        UpdateResponse updateResponse;
        try {
            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CustomizeException("更新出了问题");
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new CustomizeException("关闭io流出了问题");
        }
        return updateResponse.getId();
    }
    public String patch(TextESPatchDTO textESPatchDTO) throws CustomizeException {
        RestHighLevelClient client = null;
        try {
            client = ElasticBuilder.createElasticClient();
        } catch (IOException e) {
            throw new CustomizeException("打开io流出了问题");
        }

        // 如果没有就插入 在后面加  upsert
        UpdateRequest updateRequest = new UpdateRequest(index, textESPatchDTO.getId().toString()).doc(JSON.toJSONString(textESPatchDTO), XContentType.JSON);

        UpdateResponse updateResponse;
        try {
            updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CustomizeException("更新出了问题");
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new CustomizeException("关闭io流出了问题");
        }
        return updateResponse.getId();
    }
    public String getById(String id) throws CustomizeException {
        RestHighLevelClient client = null;
        try {
            client = ElasticBuilder.createElasticClient();
        } catch (IOException e) {
            throw new CustomizeException("打开io流出了问题");
        }
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse getResponse = null;
        try {
            getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CustomizeException("查询出了问题");
        }
        String sourceAsString = null;
        if (getResponse.isExists()) {
//            long version = getResponse.getVersion();
            sourceAsString = getResponse.getSourceAsString();
//            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
//            byte[] sourceAsBytes = getResponse.getSourceAsBytes();
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new CustomizeException("关闭io流出了问题");
        }
        if (sourceAsString != null) {
            return sourceAsString;
        } else {
            throw new CustomizeException("没有");
        }


    }

    public ArrayList<TextESDTO> getList(String text) throws CustomizeException {
        RestHighLevelClient client = null;
        try {
            client = ElasticBuilder.createElasticClient();
        } catch (IOException e) {
            throw new CustomizeException("打开io流出了问题");
        }
        SearchRequest searchRequest = new SearchRequest("note");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CustomizeException("查询出了问题");
        }
        searchResponse.getHits().toString();
        System.out.println(searchResponse.getHits().getHits());
        System.out.println(searchResponse.getHits());
        SearchHits searchHits = searchResponse.getHits();
        ArrayList<TextESDTO> arrayList = new ArrayList<TextESDTO>();

        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getSourceAsString());
            arrayList.add(JSON.parseObject(searchHit.getSourceAsString(), TextESDTO.class));
        }

        return arrayList;

    }
}
