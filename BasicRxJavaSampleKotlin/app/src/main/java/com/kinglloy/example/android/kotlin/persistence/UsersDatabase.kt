package com.kinglloy.example.android.kotlin.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * The Room database that contains the Users table
 * @author jinyalin
 * @since 2018/1/22.
 */
@Database(entities = arrayOf(User::class), version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        UsersDatabase::class.java, "Sample.db")
                        .build()
    }

}