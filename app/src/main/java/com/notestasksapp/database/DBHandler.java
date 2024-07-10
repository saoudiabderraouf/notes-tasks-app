package com.notestasksapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.notestasksapp.model.Note;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "session_db";
    private static final int DB_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String settings = "CREATE TABLE settings (id INTEGER PRIMARY KEY AUTOINCREMENT, name Text,state INTEGER)";
        String notes = "CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, color TEXT" +
                ", date_added TEXT, date_updated TEXT)";
        db.execSQL(settings);
        db.execSQL(notes);
    }

    public boolean isEmptyTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName , null);
        int count = cursor.getCount();
        cursor.close();
        return count == 0;
    }

    public void addSetting(String name, int state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("state", state);
        db.insert("settings", null, values);
        db.close();
    }
    public int getSetting(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM settings WHERE name = '" + name + "'" , null);
        cursor.moveToFirst();
        int state = cursor.getInt(2);
        cursor.close();
        return state;
    }
    public void updateSetting(String name, int state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("state", state);
        db.update("settings", values, "name=?", new String[]{name});
        db.close();
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", note.component2());
        values.put("description", note.component3());
        values.put("color", note.component4());
        values.put("date_added", note.component5());
        values.put("date_updated", note.component6());
        db.insert("notes", null, values);
        db.close();
    }
    public boolean isNoteExist(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notes WHERE id = " + id.toString() , null);
        int count = cursor.getCount();
        cursor.close();
        return count != 0;
    }
    public Note getNote(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notes WHERE id = " + id.toString() , null);
        cursor.moveToFirst();
        String title = cursor.getString(1);
        String description = cursor.getString(2);
        String color = cursor.getString(3);
        String date_added = cursor.getString(4);
        String date_updated = cursor.getString(5);
        cursor.close();
        return new Note(id, title, description, color, date_added, date_updated);
    }
    public void updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", note.component2());
        values.put("description", note.component3());
        values.put("color", note.component4());
        values.put("date_added", note.component5());
        values.put("date_updated", note.component6());
        db.update("notes", values, "id=?", new String[]{String.valueOf(note.getId())});
        db.close();
    }
    public ArrayList<Note> getNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notes" , null);
        ArrayList<Note> notes = new ArrayList();

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String color = cursor.getString(3);
                String date_added = cursor.getString(4);
                String date_updated = cursor.getString(5);
                notes.add(new Note(id, title, description, color, date_added, date_updated));
            } while(cursor.moveToNext());
        }
        cursor.close();

        return notes;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS settings");
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }
}
