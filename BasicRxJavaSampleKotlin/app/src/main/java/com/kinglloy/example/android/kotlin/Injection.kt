package com.kinglloy.example.android.kotlin

import android.content.Context
import com.kinglloy.example.android.kotlin.persistence.UserDao
import com.kinglloy.example.android.kotlin.persistence.UsersDatabase
import com.kinglloy.example.android.kotlin.ui.ViewModelFactory

/**
 * Enables injection of data sources.
 * @author jinyalin
 * @since 2018/1/22.
 */

object Injection {
    fun provideUserDataSource(context: Context): UserDao {
        val database = UsersDatabase.getInstance(context)
        return database.userDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = provideUserDataSource(context)
        return ViewModelFactory(dataSource)
    }
}