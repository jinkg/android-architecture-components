package com.kinglloy.android.persistence.migrations;

import java.util.Random;

/**
 * Model for a user that is saved in the SQLite database and used in the UI.
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

public class User {
    private int mId;

    private String mUserName;

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
