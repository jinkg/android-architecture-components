package com.kinglloy.android.persistence.migrations;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the user locally.
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

public final class UserPersistenceContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private UserPersistenceContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class UserEntity implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_ENTRY_ID = "userid";
        public static final String COLUMN_NAME_USERNAME = "username";
    }
}
