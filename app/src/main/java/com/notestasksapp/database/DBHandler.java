package com.notestasksapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.notestasksapp.model.Note;
import com.notestasksapp.model.Task;
import com.notestasksapp.model.TodoItem;

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
        String tasks = "CREATE TABLE tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, category TEXT" +
                ", start_date INTEGER, end_date INTEGER, finished INTEGER)";
        String todos = "CREATE TABLE todos (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, finished INTEGER, task INTEGER)";
        db.execSQL(settings);
        db.execSQL(notes);
        db.execSQL(tasks);
        db.execSQL(todos);
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
    public boolean isNoteExist(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM notes WHERE id = " + id.toString() , null);
        int count = cursor.getCount();
        cursor.close();
        return count != 0;
    }
    public Note getNote(Long id) {
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
                long id = cursor.getLong(0);
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

    public long addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", task.component2());
        values.put("description", task.component3());
        values.put("category", task.component4());
        values.put("start_date", task.component5());
        values.put("end_date", task.component6());
        values.put("finished", task.component7());
        long id = db.insert("tasks", null, values);
        db.close();

        return id;
    }
    public boolean isTaskExist(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks WHERE id = " + id.toString() , null);
        int count = cursor.getCount();
        cursor.close();
        return count != 0;
    }
    public Task getTask(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks WHERE id = " + id.toString() , null);
        cursor.moveToFirst();
        String title = cursor.getString(1);
        String description = cursor.getString(2);
        String category = cursor.getString(3);
        int start_date = cursor.getInt(4);
        int end_date = cursor.getInt(5);
        int finished = cursor.getInt(6);
        cursor.close();
        return new Task(id, title, description, category, start_date, end_date, finished);
    }
    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", task.component2());
        values.put("description", task.component3());
        values.put("category", task.component4());
        values.put("start_date", task.component5());
        values.put("end_date", task.component6());
        values.put("finished", task.component7());
        db.update("tasks", values, "id=?", new String[]{String.valueOf(task.getId())});
        db.close();
    }
    public ArrayList<Task> getTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks" , null);
        ArrayList<Task> tasks = new ArrayList();

        if(cursor.moveToFirst()){
            do{
                long id = cursor.getLong(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String category = cursor.getString(3);
                int start_date = cursor.getInt(4);
                int end_date = cursor.getInt(5);
                int finished = cursor.getInt(6);
                tasks.add(new Task(id, title, description, category, start_date, end_date, finished));
            } while(cursor.moveToNext());
        }
        cursor.close();

        return tasks;
    }

    public long addTodo(TodoItem todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", todo.component2());
        values.put("finished", todo.component3());
        values.put("task", todo.component4());
        long id = db.insert("todos", null, values);
        db.close();

        return id;
    }
    public boolean isTodoExist(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todos WHERE id = " + id.toString() , null);
        int count = cursor.getCount();
        cursor.close();
        return count != 0;
    }
    public TodoItem getTodo(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todos WHERE id = " + id.toString() , null);
        cursor.moveToFirst();
        String title = cursor.getString(1);
        int finished = cursor.getInt(2);
        long task = cursor.getLong(3);
        cursor.close();
        return new TodoItem(id, title, finished, task);
    }
    public void updateTodo(TodoItem todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", todo.component2());
        values.put("finished", todo.component3());
        values.put("task", todo.component4());
        db.update("todos", values, "id=?", new String[]{String.valueOf(todo.getId())});
        db.close();
    }
    public ArrayList<TodoItem> getTodos() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todos" , null);
        ArrayList<TodoItem> todos = new ArrayList();

        if(cursor.moveToFirst()){
            do{
                long id = cursor.getLong(0);
                String title = cursor.getString(1);
                int finished = cursor.getInt(2);
                long task = cursor.getLong(3);
                todos.add(new TodoItem(id, title, finished, task));
            } while(cursor.moveToNext());
        }
        cursor.close();

        return todos;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS settings");
        db.execSQL("DROP TABLE IF EXISTS notes");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        db.execSQL("DROP TABLE IF EXISTS todos");
        onCreate(db);
    }
}
