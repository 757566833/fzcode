package com.fzcode.noteservice;

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
import java.util.concurrent.TimeUnit;

@SpringBootTest
class NoteServiceApplicationTests {

    @Autowired
    TextElasticService textElasticService;

    @Test
    void contextLoads() throws CustomizeException, IOException {

//        ArrayList<String> arrayList = new ArrayList<String>();
//        arrayList.add("测试2tag");
//        NoteDTO noteDTO = new NoteDTO("2", "测试2", "测试子标题2", "测试正文2", arrayList);
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
            searchResponse =  client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new CustomizeException("查询出了问题");
        }
        searchResponse.getHits().toString();
        System.out.println(searchResponse.getHits().getHits());
        System.out.println(searchResponse.getHits());
        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getSourceAsString());
        }
        client.close();
    }

}
