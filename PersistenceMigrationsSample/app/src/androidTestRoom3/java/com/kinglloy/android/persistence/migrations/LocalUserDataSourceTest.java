package com.kinglloy.android.persistence.migrations;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Integration tests for the {@link LocalUserDataSource} implementation with Room.
 *
 * @author jinyalin
 * @since 2018/2/2.
 */

public class LocalUserDataSourceTest {
    private static final User USER = new User("id", "username", new Date());

    private UsersDatabase mDatabase;
    private LocalUserDataSource mDataSource;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                UsersDatabase.class).build();
        mDataSource = new LocalUserDataSource(mDatabase.userDao());
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void insertAndGetUser() {
        // When inserting a new user in the data source
        mDataSource.insertOrUpdateUser(USER);

        // The user can be retrieved
        User dbUser = mDataSource.getUser();
        assertEquals(dbUser.getId(), USER.getId());
        assertEquals(dbUser.getUserName(), USER.getUserName());
        assertEquals(dbUser.getDate(), USER.getDate());
    }

    @Test
    public void updateAndGetUser() {
        // Given that we have a user in the data source
        mDataSource.insertOrUpdateUser(USER);

        // When we are updating the name of the user
        User updatedUser = new User(USER.getId(), "new username", USER.getDate());
        mDataSource.insertOrUpdateUser(updatedUser);

        // The retrieved user has the updated username
        User dbUser = mDataSource.getUser();
        assertEquals(dbUser.getId(), USER.getId());
        assertEquals(dbUser.getDate(), USER.getDate());
        assertEquals(dbUser.getUserName(), "new username");
    }

    @Test
    public void deleteAndGetUser() {
        // Given that we have a user in the data source
        mDataSource.insertOrUpdateUser(USER);

        // When we are deleting all users
        mDataSource.deleteAllUsers();

        // The user is no longer in the data source
        User dbUser = mDataSource.getUser();
        assertNull(dbUser);
    }
}
