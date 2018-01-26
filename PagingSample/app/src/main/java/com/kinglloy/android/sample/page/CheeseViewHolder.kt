package com.kinglloy.android.sample.page

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

/**
 * A simple ViewHolder that can bind a Cheese item. It also accepts null items since the data may
 * not have been fetched before it is bound.
 * @author jinyalin
 * @since 2018/1/26.
 */
class CheeseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cheese_item, parent, false)) {
    private val nameView = itemView.findViewById<TextView>(R.id.name)
    var cheese: Cheese? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(cheese: Cheese?) {
        this.cheese = cheese
        nameView.text = cheese?.name
    }
}