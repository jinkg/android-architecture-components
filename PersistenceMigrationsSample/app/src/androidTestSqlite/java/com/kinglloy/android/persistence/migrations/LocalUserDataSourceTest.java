package com.kinglloy.android.persistence.migrations;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Integration tests for {@link LocalUserDataSource}.
 *
 * @author jinyalin
 * @since 2018/2/2.
 */

@RunWith(AndroidJUnit4.class)
public class LocalUserDataSourceTest {
    private LocalUserDataSource mDataSource;

    private static final User USER = new User(1, "username");

    @Before
    public void initDb() throws Exception {
        mDataSource = LocalUserDataSource.getInstance(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void cleanUp() {
        mDataSource.deleteAllUsers();
    }

    @Test
    public void insertAndGetUser() {
        // When inserting a new user in the data source
        mDataSource.insertOrUpdateUser(USER);

        //The user can be retrieved
        User dbUser = mDataSource.getUser();
        assertEquals(dbUser.getId(), USER.getId());
        assertEquals(dbUser.getUserName(), USER.getUserName());
    }

    @Test
    public void updateAndGetUser() {
        // Given that we have a user in the data source
        mDataSource.insertOrUpdateUser(USER);

        // When we are updating the name of the user
        User updatedUser = new User(USER.getId(), "new username");
        mDataSource.insertOrUpdateUser(updatedUser);

        // The retrieved user has the updated username
        User dbUser = mDataSource.getUser();
        assertEquals(dbUser.getId(), USER.getId());
        assertEquals(dbUser.getUserName(), "new username");
    }

    @Test
    public void deleteAndGetUser() {
        // Given that we have a user in the data source
        mDataSource.insertOrUpdateUser(USER);
        //When we are deleting all users
        mDataSource.deleteAllUsers();

        // The user is no longer in the data source
        User dbUser = mDataSource.getUser();
        assertNull(dbUser);
    }
}
