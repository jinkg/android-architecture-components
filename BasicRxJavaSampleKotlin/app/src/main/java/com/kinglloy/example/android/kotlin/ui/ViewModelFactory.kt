package com.kinglloy.example.android.kotlin.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kinglloy.example.android.kotlin.persistence.UserDao

/**
 * Factory for ViewModels
 * @author jinyalin
 * @since 2018/1/22.
 */
class ViewModelFactory(private val dataSource: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}