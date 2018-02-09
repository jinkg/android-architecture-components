package com.kinglloy.android.github.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * A value holder that automatically clears the reference if the Fragment's view is destroyed.
 *
 * @param <T>
 * @author jinyalin
 * @since 2018/2/9.
 */

public class AutoClearedValue<T> {
    private T value;

    public AutoClearedValue(Fragment fragment, T value) {

        FragmentManager fragmentManager = fragment.getFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
                if (f == fragment) {
                    AutoClearedValue.this.value = null;
                    fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                }
            }
        }, false);
        this.value = value;
    }
}
