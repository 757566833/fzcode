package com.fzcode.servicenote.repositroy;

import com.fzcode.servicenote.entity.Note;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NoteRepository  extends ElasticsearchRepository<Note, String> {
}
