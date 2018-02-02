package com.kinglloy.android.persistence.migrations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class for creating the test database version 1 with SQLite.
 *
 * @author jinyalin
 * @since 2018/2/2.
 */

public class SqliteTestDbOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public SqliteTestDbOpenHelper(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users ( userid INTEGER PRIMARY KEY, username TEXT )");
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
