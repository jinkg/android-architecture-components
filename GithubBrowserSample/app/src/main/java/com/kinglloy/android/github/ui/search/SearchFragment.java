package com.kinglloy.android.github.ui.search;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingComponent;
import android.support.v4.app.Fragment;

import com.kinglloy.android.github.binding.FragmentDataBindingComponent;
import com.kinglloy.android.github.databinding.SearchFragmentBinding;
import com.kinglloy.android.github.ui.common.NavigationController;
import com.kinglloy.android.github.util.AutoClearedValue;

import javax.inject.Inject;

/**
 * @author jinyalin
 * @since 2018/2/8.
 */

public class SearchFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<SearchFragmentBinding> binding;

}
