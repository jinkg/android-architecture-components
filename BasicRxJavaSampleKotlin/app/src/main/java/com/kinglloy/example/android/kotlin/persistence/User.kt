package com.kinglloy.example.android.kotlin.persistence

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * @author jinyalin
 * @since 2018/1/22.
 */
@Entity(tableName = "users")
data class User(@PrimaryKey
                @ColumnInfo(name = "userid")
                val id: String = UUID.randomUUID().toString(),
                @ColumnInfo(name = "username")
                val userName: String)