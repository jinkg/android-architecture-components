package com.kinglloy.example.android.kotlin.persistence

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

/**
 * Data Access Object for the users table.
 * @author jinyalin
 * @since 2018/1/22.
 */
@Dao
interface UserDao {
    /**
     * Get a user by id.

     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM users WHERE userid = :id")
    fun getUserById(id: String): Flowable<User>

    /**
     * Insert a user in the database. If the user already exists, replace it.

     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    /**
     * Delete all users.
     */
    @Query("DELETE FROM users")
    fun deleteAllUsers()
}