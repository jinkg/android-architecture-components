package com.kinglloy.example.android.observability;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.kinglloy.example.android.observability.persistence.LocalUserDataSource;
import com.kinglloy.example.android.observability.persistence.User;
import com.kinglloy.example.android.observability.persistence.UsersDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Integration tests for the {@link LocalUserDataSource} implementation with Room.
 *
 * @author jinyalin
 * @since 2018/1/22.
 */

public class LocalUserDataSourceTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private static final User USER = new User("id", "username");

    private UsersDatabase mDatabase;
    private LocalUserDataSource mDataSource;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                UsersDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        mDataSource = new LocalUserDataSource(mDatabase.userDao());
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getUsersWhenNoUserInserted() {
        mDataSource.getUser()
                .test()
                .assertNoValues();
    }

    @Test
    public void insertAndGetUser() {
        // When inserting a new user in the data source
        mDataSource.insertOrUpdateUser(USER);

        // When subscribing to the emissions of the user
        mDataSource.getUser()
                .test()
                // assertValue asserts that there was only one emission of the user
                .assertValue(user -> {
                    // The emitted user is the expected one
                    return user != null && user.getId().equals(USER.getId())
                            && user.getUserName().equals(USER.getUserName());
                });
    }

    @Test
    public void updateAndGetUser() {
        // Given that we have a user in the data source
        mDataSource.insertOrUpdateUser(USER);

        // When we are updating the name of the user
        User updatedUser = new User(USER.getId(), "new username");
        mDataSource.insertOrUpdateUser(updatedUser);

        // When subscribing to the emissions of the user
        mDataSource.getUser()
                .test()
                // assertValue asserts that there was only one emission of the user
                .assertValue(user -> {
                    // The emitted user is the expected one
                    return user != null && user.getId().equals(USER.getId())
                            && user.getUserName().equals("new username");
                });
    }

    @Test
    public void deleteAndGetUser() {
        // Given that we have a user in the data source
        mDataSource.insertOrUpdateUser(USER);

        //When we are deleting all users
        mDataSource.deleteAllUsers();
        // When subscribing to the emissions of the user
        mDataSource.getUser()
                .test()
                // check that there's no user emitted
                .assertNoValues();
    }
}
