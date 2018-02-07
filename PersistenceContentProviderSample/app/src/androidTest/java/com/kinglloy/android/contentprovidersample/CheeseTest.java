package com.kinglloy.android.contentprovidersample;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.kinglloy.android.contentprovidersample.data.Cheese;
import com.kinglloy.android.contentprovidersample.data.SampleDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author jinyalin
 * @since 2018/2/7.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class CheeseTest {
    private SampleDatabase mDatabase;

    @Before
    public void createDatabase() {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(),
                SampleDatabase.class).build();
    }

    @After
    public void closeDatabase() throws IOException {
        mDatabase.close();
    }

    @Test
    public void insertAndCount() {
        assertThat(mDatabase.cheese().count(), is(0));
        Cheese cheese = new Cheese();
        cheese.name = "abc";
        mDatabase.cheese().insert(cheese);
        assertThat(mDatabase.cheese().count(), is(1));
    }
}
