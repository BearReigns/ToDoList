package com.reigns.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoListDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private ArrayList<String> list;

    public TodoListDatabaseHelper(Context context) {
        super(context, "todoList.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE taskList(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "task VARCHAR(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("task", task);

        long id = db.insert("taskList", null, values);
        if (id != 0) {
            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
        }
        db.close();
        return id;
    }
    public ArrayList<String> queryAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor information = db.query("taskList", null, null, null, null, null, null);
        if (information.getCount() == 0) {
            information.close();
            db.close();
            return null;
        } else {
            list = new ArrayList();
            while (information.moveToNext()) {
                list.add(information.getString(1));
                }
            information.close();
            db.close();
            return list;
        }
    }
    public String queryByTask(String task) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("taskList", null, "task=?   ",
                new String[]{task}, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return "NULL";
        } else {
            cursor.moveToFirst();
            String res = cursor.getString(1);
            cursor.close();
            db.close();
            return res;
        }
    }
    public long delete(String s) {
        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.delete("taskList", "task=?",new String[]{s});
        if (id != 0) {
            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
        }
        db.close();
        return id;
    }
}
