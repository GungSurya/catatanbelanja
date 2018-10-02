package com.example.acere5vv02.pemrogramankomputerpembukuan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Acer E5 Vv02 on 9/22/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "catatanBelanja.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USER = "user_tbl";
    public static final String TABLE_LEDGER = "ledger_tbl";
    public static final String TABLE_LEDGER_DETAIL = "detailLedger_tbl";

    //query create table user
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT, lastName  TEXT, email TEXT, phone TEXT, photos BLOB)";
    //query create table ledger
    private static final String CREATE_TABLE_LEDGER = "CREATE TABLE " + TABLE_LEDGER + " (id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, title TEXT, dateCreate TEXT)";
    //query create table ledger detail
    private static final String CREATE_TABLE_LEDGER_DETAIL = "CREATE TABLE " + TABLE_LEDGER_DETAIL + " (id INTEGER PRIMARY KEY AUTOINCREMENT, ledgerId Integer, name TEXT, item INTEGER, price INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_LEDGER);
        db.execSQL(CREATE_TABLE_LEDGER_DETAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEDGER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEDGER_DETAIL);

        // create new tables
        onCreate(db);
    }

    public Cursor getAllDataUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        return res;
    }

    public int getUserCount(){
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public  boolean insertUser(String firstName, String lastName, String email, String phone, byte[] photo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("photos", photo);
        long result = db.insert(TABLE_USER, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}
