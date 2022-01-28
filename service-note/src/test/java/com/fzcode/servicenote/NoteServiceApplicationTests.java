package com.fzcode.servicenote;

import com.fzcode.servicenote.service.TextService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoteServiceApplicationTests {
    @Autowired
    private TextService textService;

    @Test
    void contextLoads() {
        textService.findAll(1,20);
    }

}
