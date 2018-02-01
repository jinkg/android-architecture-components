package com.kinglloy.android.persistence.migrations;

import android.support.annotation.Nullable;

/**
 * Listens for users's actions from the UI {@link UserActivity}, retrieves the data and updates
 * the UI as required.
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

public class UserPresenter {
    private UserRepository mDataSource;

    @Nullable
    private UserView mView;

    private LoadUserCallback mLoadUserCallback;
    private UpdateUserCallback mUpdateUserCallback;

    public UserPresenter(UserRepository dataSource, @Nullable UserView view) {
        mDataSource = dataSource;
        mView = view;

        mLoadUserCallback = createLoadUserCallback();
        mUpdateUserCallback = createUpdateUserCallback();
    }

    /**
     * Start working with the view.
     */
    public void start() {
        mDataSource.getUser(mLoadUserCallback);
    }

    public void stop() {
    }

    public void destory() {
        mView = null;
    }

    /**
     * Update the username of the user.
     *
     * @param userName the new userName
     */
    public void updateUserName(final String userName) {
        mDataSource.updateUserName(userName, mUpdateUserCallback);
    }

    private LoadUserCallback createLoadUserCallback() {
        return new LoadUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                if (mView != null) {
                    mView.showUserName(user.getUserName());
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView != null) {
                    mView.hideUserName();
                }
            }
        };
    }

    private UpdateUserCallback createUpdateUserCallback() {
        return user -> {
            if (mView != null) {
                mView.showUserName(user.getUserName());
            }
        };
    }
}
