package com.kinglloy.android.persistence.migrations;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Test the implementation of {@link UserDao}
 *
 * @author jinyalin
 * @since 2018/2/2.
 */
@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    private static final User USER = new User(1, "username");

    private UsersDatabase mDatabase;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                UsersDatabase.class).build();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void insertAndGetUser() {
        // When inserting a new user in the data source
        mDatabase.userDao().insertUser(USER);

        // The user can be retrieved
        User dbUser = mDatabase.userDao().getUser();
        assertEquals(dbUser.getId(), USER.getId());
        assertEquals(dbUser.getUserName(), USER.getUserName());
    }

    @Test
    public void updateAndGetUser() {
        // Given that we have a user in the data source
        mDatabase.userDao().insertUser(USER);

        // When we are updating the name of the user
        User updateUser = new User(USER.getId(), "new username");
        mDatabase.userDao().insertUser(updateUser);

        // The retrieved user has the updated username
        User dbUser = mDatabase.userDao().getUser();
        assertEquals(dbUser.getId(), USER.getId());
        assertEquals(dbUser.getUserName(), "new username");
    }

    @Test
    public void deleteAndGetUser() {
        // Given that we have a user in the data source
        mDatabase.userDao().insertUser(USER);

        // When we are deleting all users
        mDatabase.userDao().deleteAllUsers();

        // The user is no longer in the data source
        User dbUser = mDatabase.userDao().getUser();
        assertNull(dbUser);
    }
}
