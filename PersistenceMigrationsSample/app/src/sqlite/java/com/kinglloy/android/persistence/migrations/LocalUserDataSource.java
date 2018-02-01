package com.kinglloy.android.persistence.migrations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import static com.kinglloy.android.persistence.migrations.UserPersistenceContract.UserEntity
        .COLUMN_NAME_ENTRY_ID;
import static com.kinglloy.android.persistence.migrations.UserPersistenceContract.UserEntity
        .COLUMN_NAME_USERNAME;
import static com.kinglloy.android.persistence.migrations.UserPersistenceContract.UserEntity
        .TABLE_NAME;

/**
 * Concrete implementation of the UserDataSource, working directly with SQLite APIs.
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

public class LocalUserDataSource implements UserDataSource {
    private static LocalUserDataSource INSTANCE;

    private UsersDbHelper mDbHelper;

    // Prevent direct instantiation.
    private LocalUserDataSource(@NonNull Context context) {
        mDbHelper = new UsersDbHelper(context);
    }

    public static LocalUserDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalUserDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public User getUser() {
        User user = null;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME_ENTRY_ID,
                COLUMN_NAME_USERNAME
        };

        // Get the user from the table. Since, for simplicity we only have one user in the database,
        // this query gets all users from the table, but limits the result to just the 1st user
        Cursor c = db.query(TABLE_NAME, projection, null, null,
                null, null, null, "1");

        if (c != null && c.moveToFirst()) {
            int itemId = c.getInt(c.getColumnIndexOrThrow(COLUMN_NAME_ENTRY_ID));
            String title = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME_USERNAME));
            user = new User(itemId, title);
        }
        if (c != null) {
            c.close();
        }
        db.close();

        return user;
    }

    @Override
    public void insertOrUpdateUser(User user) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ENTRY_ID, user.getId());
        values.put(COLUMN_NAME_USERNAME, user.getUserName());

        db.insertWithOnConflict(TABLE_NAME, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }

    @Override
    public void deleteAllUsers() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(TABLE_NAME, null, null);

        db.close();
    }
}
