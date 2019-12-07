package com.example.nullname.passwordmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PasswordDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PasswordDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "passwordStorage.db";
    private static final int DATABASE_VERSION = 1;

    public PasswordDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PASSWORD_TABLE = "CREATE TABLE " + StoregeContract.PasswordsEntry.TABLE_NAME + "("
                                                           + StoregeContract.PasswordsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                           + StoregeContract.PasswordsEntry.COLUMN_PASSWORD + " TEXT NOT NULL, "
                                                           + StoregeContract.PasswordsEntry.COLUMN_NAME_SERVISE + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_PASSWORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
