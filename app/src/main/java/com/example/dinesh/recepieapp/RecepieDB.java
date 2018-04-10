package com.example.dinesh.recepieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
    public static final String col_6 = "RATING";
    public static final String KEY_WORD = "DESCRIPTION";
    public static final String TAG = "RecepieDB.java";



    public RecepieDB(Context context) {
        super(context, database_name, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DESCRIPTION TEXT,INGREDIENTS TEXT,INSTRUCTIONS TEXT,URL TEXT,RATING INFO)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_name);
        onCreate(db);
    }

    public boolean insertData( String description, String ingredients, String instructions, String url, float rating_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(col_2, description);
        contentValues.put(col_3, ingredients);
        contentValues.put(col_4, instructions);
        contentValues.put(col_5, url);
        contentValues.put(col_6, rating_value);
        long result = db.insert(table_name, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getRecepiesFromDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " +table_name,null);

        return result;
    }

    public boolean updateData( String id, String desc, String ingred, String instr, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int result=0;

        contentValues.put(col_2, desc);
        contentValues.put(col_3, ingred);
        contentValues.put(col_4, instr);
        contentValues.put(col_5, url);

        result = db.update(table_name, contentValues, "ID= ?", new String[] {id});
        if(result == 0)
            return false;
        else
            return true;
    }

    public boolean updateRating(String id, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int result = 0;

        contentValues.put(col_6, rating);
        result = db.update(table_name, contentValues, "ID= ?", new String[] {id});

        if(result == 0)
            return false;
        else
            return true;

    }

    public int deleteData( String id ) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = 0;
        result = db.delete(table_name, "ID = ?", new String [] {id});
        return result;
    }

    // Reference : https://github.com/google-developer-training/android-fundamentals/blob/master/WordListSqlSearchable/app/src/main/java/com/android/example/wordlistsqlsearchable/WordListOpenHelper.java
    public Cursor search(String searchString) {

        Cursor cursor = null;

        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery("select * from recepies_table where DESCRIPTION like'%"+searchString+"%'",null);

        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            //cursor = db.query(table_name, columns, where, whereArgs, null, null, null);
            /*String sql = "select * from " +table_name + "WHERE" + KEY_WORD + "Like '%" + searchString + "%'";
            //cursor = db.rawQuery("select * from " +table_name + "WHERE" + KEY_WORD + "Like %" + searchString + "%");
            cursor = db.rawQuery(sql, null);*/
            Log.i(TAG, "WOW, inside try");
            if(cursor == null) {
                Log.i(TAG, "WOW, problem problem probelm");
            }
            Log.i(TAG, "String to be searched is = " + searchString);
            if(cursor != null) {
                return cursor;
            }

        } catch (Exception e) {
            Log.i(TAG, "WOW EXCEPTION");
            return null;
        }
        return cursor;
    }

}
