package com.elamed.timerclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASENAME = "timerDB";
    public static final int DATABASE_VERSION = 2;

    public static final String TIME_TABLE = "time_table";
    public static final String ID = "id";
    public static final String TIME = "time";

    public DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TIME_TABLE+ " ("+ID+" integer not null primary key autoincrement unique, "
                        + TIME+" integer not null unique)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("drop table if exists " + TIME_TABLE);
            onCreate(db);
        }
    }
}
