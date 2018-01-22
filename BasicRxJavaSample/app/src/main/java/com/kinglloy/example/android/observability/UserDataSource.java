package com.kinglloy.example.android.observability;

import com.kinglloy.example.android.observability.persistence.User;

import io.reactivex.Flowable;

/**
 * Access point for managing user data.
 *
 * @author jinyalin
 * @since 2018/1/21.
 */

public interface UserDataSource {
    /**
     * Gets the user from the data source.
     *
     * @return the user from the data source.
     */
    Flowable<User> getUser();

    /**
     * Inserts the user into the data source, or, if this is an existing user, updates it.
     *
     * @param user the user to be inserted or updated.
     */
    void insertOrUpdateUser(User user);

    /**
     * Deletes all users from the data source.
     */
    void deleteAllUsers();
}
