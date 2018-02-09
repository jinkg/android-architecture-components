package com.kinglloy.android.github.binding;

import android.databinding.DataBindingComponent;
import android.support.v4.app.Fragment;

/**
 * A Data Binding Component implementation for fragments.
 *
 * @author jinyalin
 * @since 2018/2/9.
 */

public class FragmentDataBindingComponent implements DataBindingComponent {
    private final FragmentBindingAdapters adapter;

    public FragmentDataBindingComponent(Fragment fragment) {
        this.adapter = new FragmentBindingAdapters(fragment);
    }

    @Override
    public FragmentBindingAdapters getFragmentBindingAdapters() {
        return adapter;
    }
}
