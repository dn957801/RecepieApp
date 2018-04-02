package com.example.dinesh.recepieapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dinesh on 01/04/18.
 */

public class RecepieDB extends SQLiteOpenHelper{

    // Database related variables
    public static final String database_name = "recepies.db";
    public static final String table_name = "recepies_table";
    public static final String col_1 = "ID";
    public static final String col_2 = "DESCRIPTION";
    public static final String col_3 = "INGREDIENTS";
    public static final String col_4 = "INSTRUCTIONS";
    public static final String col_5 = "URL";



    public RecepieDB(Context context) {
        super(context, database_name, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DESCRIPTION TEXT,INGREDIENTS TEXT,INSTRUCTIONS TEXT,URL TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_name);
    }
}
