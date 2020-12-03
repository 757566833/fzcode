package com.fzcode.noteservice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pub/elastic/search", consumes = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {
}
