package com.example.testtask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_ACCOUNTS = "TABLE_ACCOUNTS";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "AMOUNT";

    public static final String TABLE_TRANSACTIONS = "TABLE_TRANSACTIONS";
    public static final String TRANSACTION_INFO = "TRANSACTION_INFO";



    public DatabaseHelper(Context context) {
        super(context,TABLE_ACCOUNTS + "AND" + TABLE_TRANSACTIONS , null, 3);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ACCOUNTS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT," + COL3 + " INTEGER)";
        db.execSQL(createTable);

        String createTable2 = "CREATE TABLE " + TABLE_TRANSACTIONS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TRANSACTION_INFO + " TEXT)";
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }
}
