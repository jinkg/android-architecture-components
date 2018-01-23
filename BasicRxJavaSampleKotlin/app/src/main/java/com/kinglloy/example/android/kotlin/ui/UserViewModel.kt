package com.kinglloy.example.android.kotlin.ui

import android.arch.lifecycle.ViewModel
import com.kinglloy.example.android.kotlin.persistence.User
import com.kinglloy.example.android.kotlin.persistence.UserDao
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * View Model for the [UserActivity]
 * @author jinyalin
 * @since 2018/1/22.
 */

class UserViewModel(private val dataSource: UserDao) : ViewModel() {
    /**
     * Get the user name of the user.

     * @return a [Flowable] that will emit every time the user name has been updated.
     */
    // for every emission of the user, get the user name
    fun userName(): Flowable<String> {
        return dataSource.getUserById(USER_ID)
                .map { user -> user.userName }
    }

    /**
     * Update the user name.
     * @param userName the new user name
     * *
     * @return a [Completable] that completes when the user name is updated
     */
    fun updateUserName(userName: String): Completable {
        return Completable.fromAction {
            val user = User(USER_ID, userName)
            dataSource.insertUser(user)
        }
    }

    companion object {
        // using a hardcoded value for simplicity
        const val USER_ID = "1"
    }
}