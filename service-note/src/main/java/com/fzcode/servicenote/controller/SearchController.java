package com.fzcode.servicenote.controller;

import com.fzcode.internalcommon.dto.common.ListDTO;
import com.fzcode.internalcommon.dto.servicenote.request.note.SearchRequest;
import com.fzcode.servicenote.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "搜索模块")
@RestController
@RequestMapping(value = "/note")
public class SearchController {
    NoteService noteService;
    @Autowired
    public void setNoteService(NoteService noteService){
        this.noteService = noteService;
    }
    @ApiOperation(value = "搜索文章")
    @GetMapping(value = "/search")
    public ListDTO<Map<String, List<String>>> search(SearchRequest searchRequest){
        return  noteService.search(searchRequest);
    }

}
