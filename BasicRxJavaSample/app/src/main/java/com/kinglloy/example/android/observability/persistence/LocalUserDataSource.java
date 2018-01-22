package com.kinglloy.example.android.observability.persistence;

import com.kinglloy.example.android.observability.UserDataSource;

import io.reactivex.Flowable;

/**
 * Using the Room database as a data source.
 *
 * @author jinyalin
 * @since 2018/1/21.
 */

public class LocalUserDataSource implements UserDataSource {
    private final UserDao mUserDao;

    public LocalUserDataSource(UserDao userDao) {
        this.mUserDao = userDao;
    }

    @Override
    public Flowable<User> getUser() {
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
