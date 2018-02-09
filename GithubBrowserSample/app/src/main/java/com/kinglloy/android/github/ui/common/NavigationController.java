package com.kinglloy.android.github.ui.common;

import android.support.v4.app.FragmentManager;

import com.kinglloy.android.github.MainActivity;
import com.kinglloy.android.github.R;
import com.kinglloy.android.github.ui.search.SearchFragment;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 *
 * @author jinyalin
 * @since 2018/2/9.
 */

public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToSearch() {
        SearchFragment searchFragment = new SearchFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, searchFragment)
                .commitAllowingStateLoss();
    }
}
