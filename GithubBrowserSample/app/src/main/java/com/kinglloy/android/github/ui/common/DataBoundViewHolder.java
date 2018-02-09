package com.kinglloy.android.github.ui.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * A generic ViewHolder that works with a {@link ViewDataBinding}.
 *
 * @param <T> The type of the ViewDataBinding.
 * @author jinyalin
 * @since 2018/2/9.
 */

public class DataBoundViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T binding;

    DataBoundViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
