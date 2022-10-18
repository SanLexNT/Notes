package com.example.notes.domain;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.notes.domain.sqliteDatabase.NoteDBHelper;
import com.example.notes.domain.sqliteDatabase.NoteEntry;

import java.util.ArrayList;
import java.util.List;

public class DBNoteRepository implements NoteRepository{

    private NoteDBHelper dbHelper;
    private SQLiteDatabase database;
    private List<Note> notes = new ArrayList<>();

    public DBNoteRepository(Context context) {

        dbHelper = new NoteDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public List<Note> getNotes() {

        notes.clear();

        Cursor cursor = database.query(NoteEntry.TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(NoteEntry._ID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(NoteEntry.COLUMN_TITLE));
            @SuppressLint("Range") String details = cursor.getString(cursor.getColumnIndex(NoteEntry.COLUMN_DETAILS));
            Note note = new Note(id, title, details);
            notes.add(note);
        }

        cursor.close();

        return notes;
    }

    @Override
    public void addNote(String title, String details) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteEntry.COLUMN_TITLE, title);
        contentValues.put(NoteEntry.COLUMN_DETAILS, details);

        database.insert(NoteEntry.TABLE_NAME, null, contentValues);

    }

    @Override
    public void editNote(Note note, String title, String details) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteEntry.COLUMN_TITLE, title);
        contentValues.put(NoteEntry.COLUMN_DETAILS, details);

        String condition = NoteEntry._ID + " = " + note.getId();

        database.update(NoteEntry.TABLE_NAME, contentValues, condition, null);
    }

    @Override
    public void removeNote(Note note) {

        String condition = NoteEntry._ID + " = " + note.getId();
        database.delete(NoteEntry.TABLE_NAME, condition, null);
    }
}
