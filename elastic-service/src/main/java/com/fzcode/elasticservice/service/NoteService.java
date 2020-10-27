package com.fzcode.elasticservice.service;

import com.alibaba.fastjson.JSON;
import com.fzcode.elasticservice.dto.NoteDTO;
import com.fzcode.elasticservice.exception.CustomizeException;
import com.fzcode.elasticservice.utils.ElasticBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class NoteService {

    @Value("${elastic.index}")
    private static String index;

    public String create(NoteDTO noteDTO) throws CustomizeException {
        RestHighLevelClient client = null;
        try {
            client = ElasticBuilder.createElasticClient();
        } catch (IOException e) {
            throw new CustomizeException("打开io流出了问题");
        }
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(noteDTO.getId());
        indexRequest.source(JSON.toJSONString(noteDTO), XContentType.JSON);
        IndexResponse indexResponse;
        try {
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CustomizeException("新增出了问题");
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new CustomizeException("关闭io流出了问题");
        }
        return indexResponse.getId();
    }
    public String delete(String id) throws CustomizeException {
        RestHighLevelClient client = null;
        try {
            client = ElasticBuilder.createElasticClient();
        } catch (IOException e) {
            throw new CustomizeException("打开io流出了问题");
        }
        DeleteRequest deleteRequest = new DeleteRequest(index,id);
        DeleteResponse deleteResponse;
        try {
            deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CustomizeException("删除出了问题");
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new CustomizeException("关闭io流出了问题");
        }
        return deleteResponse.getId();
    }

    public String update(NoteDTO noteDTO) throws CustomizeException {
        RestHighLevelClient client = null;
        try {
            client = ElasticBuilder.createElasticClient();
        } catch (IOException e) {
            throw new CustomizeException("打开io流出了问题");
        }
        // 如果没有就插入 在后面加  upsert
        UpdateRequest updateRequest = new UpdateRequest(index, noteDTO.getId()).doc(JSON.toJSONString(noteDTO), XContentType.JSON);

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

    public String getDataById(String id) throws CustomizeException {
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
            long version = getResponse.getVersion();
            sourceAsString = getResponse.getSourceAsString();
            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
            byte[] sourceAsBytes = getResponse.getSourceAsBytes();
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new CustomizeException("关闭io流出了问题");
        }

        return sourceAsString;
    }
}
