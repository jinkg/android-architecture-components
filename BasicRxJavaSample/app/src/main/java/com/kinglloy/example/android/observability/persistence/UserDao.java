package com.kinglloy.example.android.observability.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.reactivex.Flowable;

/**
 * Data Access Object for the users table.
 *
 * @author jinyalin
 * @since 2018/1/21.
 */
@Dao
public interface UserDao {
    /**
     * Get the user from the table. Since for simplicity we only have one user in the database,
     * this query gets all users from the table, but limits the result to just the 1st user.
     *
     * @return the user from the table
     */
    @Query("SELECT * FROM users LIMIT 1")
    Flowable<User> getUser();

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    /**
     * Delete all users.
     */
    @Query("DELETE FROM users")
    void deleteAllUsers();
}
