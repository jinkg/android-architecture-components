package com.kinglloy.android.persistence.migrations;

import android.support.annotation.MainThread;

/**
 * Callback used in notifying when the user has been updated.
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

public interface UpdateUserCallback {
    /**
     * Method called when the user was updated.
     *
     * @param user the updated user.
     */
    @MainThread
    void onUserUpdated(User user);
}
