package com.kinglloy.android.persistence.migrations;

/**
 * Specify the contract between the view and the presenter
 *
 * @author jinyalin
 * @since 2018/2/1.
 */

public interface UserView {
    /**
     * Display a userName on the screen
     *
     * @param userName the userName
     */
    void showUserName(String userName);

    /**
     * Hide the userName field.
     */
    void hideUserName();
}
