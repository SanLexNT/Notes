package com.example.notes.domain.sqliteDatabase;

import android.provider.BaseColumns;

public class NoteEntry implements BaseColumns {

    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DETAILS = "details";

    public static final String TYPE_INT = "INTEGER";
    public static final String TYPE_STRING = "TEXT";

    public static final String CREATE_COMMAND = String.format("CREATE TABLE IF NOT EXISTS %s (%s %s PRIMARY KEY AUTOINCREMENT, %s %s, %s %s)",
            TABLE_NAME, _ID, TYPE_INT, COLUMN_TITLE, TYPE_STRING, COLUMN_DETAILS, TYPE_STRING);

    public static final String DROP_COMMAND = String.format("DROP TABLE IF EXISTS ", TABLE_NAME);
}
