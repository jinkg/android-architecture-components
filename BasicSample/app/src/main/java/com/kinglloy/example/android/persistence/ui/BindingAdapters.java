package com.kinglloy.example.android.persistence.ui;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * @author jinyalin
 * @since 2018/1/19.
 */

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
