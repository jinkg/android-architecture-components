package com.kinglloy.android.sample.page

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Data class that represents our items.
 * @author jinyalin
 * @since 2018/1/26.
 */
@Entity
data class Cheese(@PrimaryKey(autoGenerate = true) val id: Int, val name: String)