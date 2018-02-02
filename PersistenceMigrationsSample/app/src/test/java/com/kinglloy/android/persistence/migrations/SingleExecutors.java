package com.kinglloy.android.persistence.migrations;

import java.util.concurrent.Executor;

/**
 * Allow instant execution of tasks.
 * Note: when using the Architecture components, for testing, you can use the
 * InstantTaskExecutorRule test rule, after adding
 * android.arch.core:core-testing to your build.gradle file.
 *
 * @author jinyalin
 * @since 2018/2/2.
 */

public class SingleExecutors extends AppExecutors {
    private static Executor instant = Runnable::run;

    public SingleExecutors() {
        super(instant, instant, instant);
    }
}
