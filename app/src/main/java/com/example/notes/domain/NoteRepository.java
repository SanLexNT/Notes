package com.example.notes.domain;

import java.util.List;

public interface NoteRepository {

    List<Note> getNotes();

    void addNote(String title, String details);

    void editNote(Note note, String title, String details);

    void removeNote(Note note);

}
