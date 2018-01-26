package com.kinglloy.android.sample.page;

import android.app.Activity;
import android.arch.core.executor.testing.CountingTaskExecutorRule;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Simply sanity test to ensure that activity launches without any issues and shows some data.
 *
 * @author jinyalin
 * @since 2018/1/26.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public CountingTaskExecutorRule testRule = new CountingTaskExecutorRule();

    @Test
    public void showSomeResults() throws InterruptedException, TimeoutException {
        Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Activity activity = InstrumentationRegistry.getInstrumentation().startActivitySync(intent);
        testRule.drainTasks(10, TimeUnit.SECONDS);
        RecyclerView recyclerView = activity.findViewById(R.id.cheeseList);
        MatcherAssert.assertThat(recyclerView.getAdapter(), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(recyclerView.getAdapter().getItemCount() > 0,
                CoreMatchers.is(true));
    }
}
