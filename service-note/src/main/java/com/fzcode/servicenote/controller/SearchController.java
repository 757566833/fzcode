package com.fzcode.servicenote.controller;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "搜索模块")
@RestController
@RequestMapping(value = "/elastic/search", consumes = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {
}
