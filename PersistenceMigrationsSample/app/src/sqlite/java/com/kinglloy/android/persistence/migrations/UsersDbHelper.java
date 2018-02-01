package com.kinglloy.android.persistence.migrations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.kinglloy.android.persistence.migrations.UserPersistenceContract.UserEntity
        .COLUMN_NAME_ENTRY_ID;
import static com.kinglloy.android.persistence.migrations.UserPersistenceContract.UserEntity
        .COLUMN_NAME_USERNAME;
import static com.kinglloy.android.persistence.migrations.UserPersistenceContract.UserEntity
        .TABLE_NAME;

/**
 * @author jinyalin
 * @since 2018/2/1.
 */

public class UsersDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Sample.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY NOT NULL," +
                    COLUMN_NAME_USERNAME + " TEXT )";

    public UsersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
