package com.example.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME= "Users.db";
    public static final String DB_TABLE= "Users_Table";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String CREATE_TABLE = "CREATE TABLE "+DB_TABLE+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" TEXT "+")";
    public DatabaseHelper(Context context) {
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
        onCreate(db);
    }
    //creating method to insert data
    public boolean insertdata(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        long result = db.insert(DB_TABLE,null,contentValues);
        return result!=-1; // if result equal to -1 then data is not inserted
    }
    //creating method to view data
    public Cursor viewdata(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+DB_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    public Integer deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DB_TABLE,"NAME = ? ",new String[] {name});
    }
}
