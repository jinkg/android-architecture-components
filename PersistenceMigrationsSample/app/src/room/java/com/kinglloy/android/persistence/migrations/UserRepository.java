package com.kinglloy.android.persistence.migrations;

import java.lang.ref.WeakReference;

/**
 * The repository is responsible of handling user data operations.
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

public class UserRepository {
    private AppExecutors mAppExecutors;

    private UserDataSource mUserDataSource;

    private User mCachedUser;

    public UserRepository(AppExecutors appExecutors, UserDataSource userDataSource) {
        mAppExecutors = appExecutors;
        mUserDataSource = userDataSource;
    }

    /**
     * Get the user from the data source, cache it and notify via the callback that the user has
     * been retrieved.
     *
     * @param callback callback that gets called when the user was retrieved from the data source.
     */
    void getUser(LoadUserCallback callback) {
        final WeakReference<LoadUserCallback> loadUserCallback = new WeakReference<>(callback);

        // request the user on the I/O thread
        mAppExecutors.diskIO().execute(() -> {
            final User user = mUserDataSource.getUser();
            // notify on the main thread
            mAppExecutors.mainThread().execute(() -> {
                final LoadUserCallback userCallback = loadUserCallback.get();
                if (userCallback == null) {
                    return;
                }
                if (user == null) {
                    userCallback.onDataNotAvailable();
                } else {
                    mCachedUser = user;
                    userCallback.onUserLoaded(mCachedUser);
                }
            });
        });
    }

    /**
     * Insert an new user or update the name of the user.
     *
     * @param userName the user name
     * @param callback callback that gets triggered when the user was updated.
     */
    void updateUserName(String userName, UpdateUserCallback callback) {
        final WeakReference<UpdateUserCallback> updateUserCallback = new WeakReference<>(callback);

        final User user = mCachedUser == null
                ? new User(userName)
                : new User(mCachedUser.getId(), userName);

        // update the user on the I/O thread
        mAppExecutors.diskIO().execute(() -> {
            mUserDataSource.insertOrUpdateUser(user);
            mCachedUser = user;
            // notify on the main thread
            mAppExecutors.mainThread().execute(() -> {
                UpdateUserCallback userCallback = updateUserCallback.get();
                if (userCallback != null) {
                    userCallback.onUserUpdated(user);
                }
            });
        });
    }
}
