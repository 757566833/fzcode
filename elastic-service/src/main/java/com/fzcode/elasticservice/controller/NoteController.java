package com.fzcode.elasticservice.controller;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/elastic/es", consumes = MediaType.APPLICATION_JSON_VALUE)
public class NoteController {
    @GetMapping(value = "/list")
    public void getList() {
//        CreateIndexRequest
    }

    public void createIndex() {
//        CreateIndexRequest
    }

    public void getById() {
//        CreateIndexRequest
    }
}
