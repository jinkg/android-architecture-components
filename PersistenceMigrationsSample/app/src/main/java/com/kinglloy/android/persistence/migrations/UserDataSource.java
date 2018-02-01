package com.kinglloy.android.persistence.migrations;

/**
 * Access point for accessing user data.
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

public interface UserDataSource {
    /**
     * Gets all the users from the data source.
     *
     * @return all the users from the data source.
     */
    User getUser();

    /**
     * Inserts the user in the data source, or, if this is an existing user, it updates it.
     *
     * @param user the user to be inserted or updated.
     */
    void insertOrUpdateUser(User user);

    /**
     * Deletes all users from the data source.
     */
    void deleteAllUsers();
}
