package com.fzcode.elasticservice.service.DB;

import com.fzcode.elasticservice.entity.Notes;
import com.fzcode.elasticservice.repositroy.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteDBService {


    NoteRepository noteRepository;

    @Autowired
    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public Notes save(Notes notes) {
        Notes notesResult = noteRepository.save(notes);
        return notesResult;
    }

    public Page<Notes> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notes> list = noteRepository.findAll(pageable);
        return list;
    }

    public Notes findById(Integer id) {
        Optional<Notes> noteResult = noteRepository.findById(id);
        return noteResult.get();
    }

    public Notes update(Notes notes) {
        Notes notesResult = noteRepository.save(notes);
        return notesResult;
    }

    public Notes patch(Notes notes) {
        if (notes.getTitle() != null && notes.getDescription() != null) {
            return noteRepository.patch(notes.getNid(), notes.getTitle(), notes.getDescription());
        } else if (notes.getTitle() != null) {
            return noteRepository.patch(notes.getNid(), notes.getTitle());
        } else if (notes.getDescription() != null) {
            return noteRepository.patch(notes.getDescription(), notes.getNid());
        } else {
            return notes;
        }
    }

    public Notes delete(Integer id) {
        Notes notes = new Notes();
        notes.setDelete(true);
        notes.setNid(id);
        Notes notesResult = noteRepository.save(notes);
        return notesResult;
    }
}
