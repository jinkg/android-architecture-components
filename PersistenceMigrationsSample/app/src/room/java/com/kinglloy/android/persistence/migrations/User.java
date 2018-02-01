package com.kinglloy.android.persistence.migrations;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Random;

/**
 * Immutable model class for a User and entity in the Room database.
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @ColumnInfo(name = "userid")
    private int mId;

    @ColumnInfo(name = "username")
    private String mUserName;

    @Ignore
    public User(String userName) {
        // DO NOT USE Integer Random values for primary keys.
        // This is using an Integer to showcase a WRONG implementation that has to be fixed
        // afterwards by updating the schema.
        // The ID is updated to a UUID String in the room3 flavor.
        mId = new Random(Integer.MAX_VALUE).nextInt();
        mUserName = userName;
    }

    public User(int id, String userName) {
        this.mId = id;
        this.mUserName = userName;
    }

    public int getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }
}
