package com.kinglloy.android.contentprovidersample.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

/**
 * Data access object for Cheese.
 *
 * @author jinyalin
 * @since 2018/2/7.
 */

@Dao
public interface CheeseDao {
    /**
     * Counts the number of cheeses in the table.
     *
     * @return The number of cheeses.
     */
    @Query("SELECT COUNT(*) FROM " + Cheese.TABLE_NAME)
    int count();

    /**
     * Inserts a cheese into the table.
     *
     * @param cheese A new cheese.
     * @return The row ID of the newly inserted cheese.
     */
    @Insert
    long insert(Cheese cheese);

    /**
     * Inserts multiple cheeses into the database
     *
     * @param cheeses An array of new cheeses.
     * @return The row IDs of the newly inserted cheeses.
     */
    @Insert
    long[] insertAll(Cheese[] cheeses);

    /**
     * Select all cheeses.
     *
     * @return A {@link Cursor} of all the cheeses in the table.
     */
    @Query("SELECT * FROM " + Cheese.TABLE_NAME)
    Cursor selectAll();

    /**
     * Select a cheese by the ID.
     *
     * @param id The row ID.
     * @return A {@link Cursor} of the selected cheese.
     */
    @Query("SELECT * FROM " + Cheese.TABLE_NAME + " WHERE " + Cheese.COLUMN_ID + " = :id")
    Cursor selectById(long id);

    /**
     * Delete a cheese by the ID.
     *
     * @param id The row ID.
     * @return A number of cheeses deleted. This should always be {@code 1}.
     */
    @Query("DELETE FROM " + Cheese.TABLE_NAME + " WHERE " + Cheese.COLUMN_ID + " = :id")
    int deleteById(long id);

    /**
     * Update the cheese. The cheese is identified by the row ID.
     *
     * @param cheese The cheese to update.
     * @return A number of cheeses updated. This should always be {@code 1}.
     */
    @Update
    int update(Cheese cheese);
}
