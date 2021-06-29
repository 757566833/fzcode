package com.fzcode.noteservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fzcode.noteservice.exception.CustomizeException;
import com.fzcode.noteservice.service.elastic.TextElasticService;
import com.fzcode.noteservice.utils.ElasticBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class NoteServiceApplicationTests {

    @Autowired
    TextElasticService textElasticService;

    @Test
    void contextLoads() throws CustomizeException, IOException {
        List<String> lisar = new ArrayList<>();
        lisar.add("123");
        lisar.add("4312");
        System.out.println(JSON.toJSONString(lisar));
        String str = "123,4312";
        List<String> list = JSON.parseArray(str, String.class);
        System.out.println(list);
    }

}
