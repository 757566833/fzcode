package com.fzcode.elasticservice;

import com.alibaba.fastjson.JSON;
import com.fzcode.elasticservice.dto.NoteDTO;
import com.fzcode.elasticservice.exception.CustomizeException;
import com.fzcode.elasticservice.service.NoteService;
import com.fzcode.elasticservice.utils.ElasticBuilder;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ElasticServiceApplicationTests {

    @Autowired
    NoteService noteService;

    @Test
    void contextLoads() throws CustomizeException {

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("测试2tag");
        NoteDTO noteDTO = new NoteDTO("2", "测试2", "测试子标题2", "测试正文2", arrayList);
        RestHighLevelClient client = null;
        try {
            client = ElasticBuilder.createElasticClient();
        } catch (IOException e) {
            throw new CustomizeException("打开io流出了问题");
        }
        // 如果没有就插入 在后面加  upsert
        IndexRequest indexRequest = new IndexRequest("note");
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
    }

}
