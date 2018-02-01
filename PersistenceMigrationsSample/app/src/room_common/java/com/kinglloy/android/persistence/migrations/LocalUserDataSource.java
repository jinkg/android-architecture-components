package com.kinglloy.android.persistence.migrations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

/**
 * Concrete implementation of the {@link LocalUserDataSource} that works with Room.
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

public class LocalUserDataSource implements UserDataSource {
    private static volatile LocalUserDataSource INSTANCE;

    private UserDao mUserDao;

    @VisibleForTesting
    LocalUserDataSource(UserDao userDao) {
        mUserDao = userDao;
    }

    public static LocalUserDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (LocalUserDataSource.class) {
                if (INSTANCE == null) {
                    UsersDatabase database = UsersDatabase.getInstance(context);
                    INSTANCE = new LocalUserDataSource(database.userDao());
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public User getUser() {
        return mUserDao.getUser();
    }

    @Override
    public void insertOrUpdateUser(User user) {
        mUserDao.insertUser(user);
    }

    @Override
    public void deleteAllUsers() {
        mUserDao.deleteAllUsers();
    }
}
