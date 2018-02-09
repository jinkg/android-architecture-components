package com.kinglloy.android.github.binding;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Data Binding adapters specific to the app.
 *
 * @author jinyalin
 * @since 2018/2/9.
 */

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
