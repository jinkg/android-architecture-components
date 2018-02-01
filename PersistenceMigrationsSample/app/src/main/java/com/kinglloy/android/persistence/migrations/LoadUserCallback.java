package com.kinglloy.android.persistence.migrations;

import android.support.annotation.MainThread;

/**
 * Callback called when the user was loaded from the repository.
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

public interface LoadUserCallback {
    /**
     * Method called when the user was loaded from the repository.
     *
     * @param user the user from repository.
     */
    @MainThread
    void onUserLoaded(User user);

    /**
     * Method called when there was no user in the repository.
     */
    @MainThread
    void onDataNotAvailable();
}
