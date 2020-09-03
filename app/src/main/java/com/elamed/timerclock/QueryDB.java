package com.elamed.timerclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QueryDB {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public QueryDB(Context context){
        this.dbHelper = new DBHelper(context);
        this.database = this.dbHelper.getReadableDatabase();
    }

    public void insertTime(long time){
        ContentValues values = new ContentValues();
        values.put(DBHelper.TIME,time);
        long i = database.insert(DBHelper.TIME_TABLE,null,values);
        Log.e("INSERT", String.valueOf(i));
        values.clear();
    }

    public List<Long> selectAllTime(){
        ArrayList<Long> times = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * from "+ DBHelper.TIME_TABLE,null);
        cursor.moveToNext();
        for (int i = 0; !cursor.isAfterLast();i++){
            Log.e("SELECT", String.valueOf(cursor.getLong(cursor.getColumnIndex(DBHelper.TIME))));
            times.add(i,cursor.getLong(cursor.getColumnIndex(DBHelper.TIME)));
            cursor.moveToNext();
        }
        cursor.close();
        return times;
    }

    public void deleteTime(int id){
        database.rawQuery("DELETE from "+ DBHelper.TIME_TABLE+ " WHERE "+DBHelper.ID + " = "+ id,null);
    }
}
