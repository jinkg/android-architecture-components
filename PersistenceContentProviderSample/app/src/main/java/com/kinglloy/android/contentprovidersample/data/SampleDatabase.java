package com.kinglloy.android.contentprovidersample.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

/**
 * The Room database.
 *
 * @author jinyalin
 * @since 2018/2/7.
 */

@Database(entities = {Cheese.class}, version = 1)
public abstract class SampleDatabase extends RoomDatabase {
    /**
     * @return The DAO for the Cheese table.
     */
    public abstract CheeseDao cheese();

    private static SampleDatabase sInstance;

    public static synchronized SampleDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), SampleDatabase.class, "ex")
                    .build();
            sInstance.populateInitialData();
        }
        return sInstance;
    }

    /**
     * Switches the internal implementation with an empty in-memory database.
     *
     * @param context The context.
     */
    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        sInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                SampleDatabase.class).build();
    }

    /**
     * Inserts the dummy data into the database if it is currently empty.
     */
    private void populateInitialData() {
        if (cheese().count() == 0) {
            Cheese cheese = new Cheese();
            beginTransaction();
            try {
                for (int i = 0; i < Cheese.CHEESES.length; i++) {
                    cheese.name = Cheese.CHEESES[i];
                    cheese().insert(cheese);
                }
                setTransactionSuccessful();
            } finally {
                endTransaction();
            }
        }
    }
}
