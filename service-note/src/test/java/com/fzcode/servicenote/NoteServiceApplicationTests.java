package com.fzcode.servicenote;

import com.fzcode.servicenote.dao.elastic.TextElasticDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoteServiceApplicationTests {

    @Autowired
    TextElasticDao textElasticDao;

//    @Test
//    void contextLoads() throws CustomizeException, IOException {
//        List<String> lisar = new ArrayList<>();
//        lisar.add("123");
//        lisar.add("4312");
//        System.out.println(JSON.toJSONString(lisar));
//        String str = "123,4312";
//        List<String> list = JSON.parseArray(str, String.class);
//        System.out.println(list);
//    }

}
