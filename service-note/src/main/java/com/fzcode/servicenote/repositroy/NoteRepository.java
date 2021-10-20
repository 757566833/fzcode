package com.fzcode.servicenote.repositroy;

import com.fzcode.servicenote.entity.NoteEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NoteRepository  extends ElasticsearchRepository<NoteEntity, String> {
}
