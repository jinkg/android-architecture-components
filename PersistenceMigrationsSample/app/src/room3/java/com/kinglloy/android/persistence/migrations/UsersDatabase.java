package com.kinglloy.android.persistence.migrations;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

/**
 * The Room database that contains the Users table
 *
 * @author jinyalin
 * @since 2018/2/1.
 */
@Database(entities = {User.class}, version = 4)
@TypeConverters(DateConverter.class)
public abstract class UsersDatabase extends RoomDatabase {
    private static UsersDatabase INSTANCE;

    public abstract UserDao userDao();

    private static final Object sLock = new Object();

    /**
     * Migrate from:
     * version 1 - using the SQLiteDatabase API
     * to
     * version 2 - using Room
     */
    @VisibleForTesting
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Room uses an own database hash to uniquely identify the database
            // Since version 1 does not use Room, it doesn't have the database hash associated.
            // By implementing a Migration class, we're telling Room that it should use the data
            // from version 1 to version 2.
            // If no migration is provided, then the tables will be dropped and recreated.
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    /**
     * Migrate from:
     * version 2 - using Room
     * to
     * version 3 - using Room where the {@link User} has an extra field: date
     */
    @VisibleForTesting
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users "
                    + " ADD COLUMN last_update INTEGER");
        }
    };

    /**
     * Migrate from:
     * version 3 - using Room where the {@link User#mId} is an int
     * to
     * version 4 - using Room where the {@link User#mId} is a String
     */
    @VisibleForTesting
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // SQLite supports a limited operations for ALTER.
            // Changing the type of a column is not directly supported, so this is what we need
            // to do:
            // Create the new table
            database.execSQL(
                    "CREATE TABLE users_new (userid TEXT NOT NULL,"
                            + "username TEXT,"
                            + "last_update INTEGER,"
                            + "PRIMARY KEY (userid))");
            // Copy the data
            database.execSQL("INSERT INTO users_new (userid, username, last_update) "
                    + "SELECT userid, username, last_update "
                    + "FROM users");

            // Remove the old table
            database.execSQL("DROP TABLE users");

            // Change the table name to the correct one
            database.execSQL("ALTER TABLE users_new RENAME TO users");
        }
    };

    /**
     * Migrate from
     * version 1 - using the SQLiteDatabase API
     * to
     * version 4 - using Room where {@link User} has a new field: {@link User#mDate} and
     * {@link User#mId} is a String
     */
    @VisibleForTesting
    static final Migration MIGRATION_1_4 = new Migration(1, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL(
                    "CREATE TABLE users_new (userid TEXT, username TEXT, last_update INTEGER,"
                            + " PRIMARY KEY(userid))");
            // Copy the data
            database.execSQL("INSERT INTO users_new (userid, username, last_update) "
                    + "SELECT userid, username, 0 "
                    + "FROM users");
            // Remove the old table
            database.execSQL("DROP TABLE users");
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE users_new RENAME TO users");
        }
    };

    public static UsersDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UsersDatabase.class, "Sample.db")
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_1_4)
                        .build();
            }
            return INSTANCE;
        }
    }
}
