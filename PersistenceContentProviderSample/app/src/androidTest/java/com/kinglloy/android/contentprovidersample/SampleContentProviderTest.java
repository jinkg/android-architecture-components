package com.kinglloy.android.contentprovidersample;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.kinglloy.android.contentprovidersample.data.Cheese;
import com.kinglloy.android.contentprovidersample.data.SampleDatabase;
import com.kinglloy.android.contentprovidersample.provider.SampleContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author jinyalin
 * @since 2018/2/7.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class SampleContentProviderTest {
    private ContentResolver mContentResolver;

    @Before
    public void setUp() {
        final Context context = InstrumentationRegistry.getTargetContext();
        SampleDatabase.switchToInMemory(context);
        mContentResolver = context.getContentResolver();
    }

    @Test
    public void cheese_initiallyEmpty() {
        final Cursor cursor = mContentResolver.query(SampleContentProvider.URI_CHEESE,
                new String[]{Cheese.COLUMN_NAME}, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(0));
        cursor.close();
    }

    @Test
    public void cheese_insert() {
        final Uri itemUri = mContentResolver.insert(SampleContentProvider.URI_CHEESE,
                cheeseWithName("Daigo"));
        assertThat(itemUri, notNullValue());
        final Cursor cursor = mContentResolver.query(SampleContentProvider.URI_CHEESE,
                new String[]{Cheese.COLUMN_NAME}, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow(Cheese.COLUMN_NAME)),
                is("Daigo"));
        cursor.close();
    }

    @Test
    public void cheese_update() {
        final Uri itemUri = mContentResolver.insert(SampleContentProvider.URI_CHEESE,
                cheeseWithName("Daigo"));
        assertThat(itemUri, notNullValue());
        final int count = mContentResolver.update(itemUri, cheeseWithName("Queso"), null, null);
        assertThat(count, is(1));

        final Cursor cursor = mContentResolver.query(SampleContentProvider.URI_CHEESE,
                new String[]{Cheese.COLUMN_NAME}, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow(Cheese.COLUMN_NAME)),
                is("Queso"));
        cursor.close();
    }

    @Test
    public void cheese_delete() {
        final Uri itemUri = mContentResolver.insert(SampleContentProvider.URI_CHEESE,
                cheeseWithName("Daigo"));
        assertThat(itemUri, notNullValue());
        final Cursor cursor1 = mContentResolver.query(SampleContentProvider.URI_CHEESE,
                new String[]{Cheese.COLUMN_NAME}, null, null, null);
        assertThat(cursor1, notNullValue());
        assertThat(cursor1.getCount(), is(1));
        cursor1.close();

        final int count = mContentResolver.delete(itemUri, null, null);
        assertThat(count, is(1));
        final Cursor cursor2 = mContentResolver.query(SampleContentProvider.URI_CHEESE,
                new String[]{Cheese.COLUMN_NAME}, null, null, null);
        assertThat(cursor2, notNullValue());
        assertThat(cursor2.getCount(), is(0));
        cursor2.close();
    }

    @Test
    public void cheese_bulkInsert() {
        final int count = mContentResolver.bulkInsert(SampleContentProvider.URI_CHEESE,
                new ContentValues[]{
                        cheeseWithName("Peynir"),
                        cheeseWithName("Queso"),
                        cheeseWithName("Diago")
                });
        assertThat(count, is(3));
        final Cursor cursor = mContentResolver.query(SampleContentProvider.URI_CHEESE,
                new String[]{Cheese.COLUMN_NAME}, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(3));
        cursor.close();
    }

    @Test
    public void cheese_applyBatch() throws RemoteException, OperationApplicationException {
        final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(ContentProviderOperation
                .newInsert(SampleContentProvider.URI_CHEESE)
                .withValue(Cheese.COLUMN_NAME, "Peynir")
                .build());
        operations.add(ContentProviderOperation
                .newInsert(SampleContentProvider.URI_CHEESE)
                .withValue(Cheese.COLUMN_NAME, "Queso")
                .build());
        final ContentProviderResult[] results = mContentResolver.applyBatch(
                SampleContentProvider.AUTHORITY, operations);
        assertThat(results.length, is(2));
        final Cursor cursor = mContentResolver.query(SampleContentProvider.URI_CHEESE,
                new String[]{Cheese.COLUMN_NAME}, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(2));
        assertThat(cursor.moveToFirst(), is(true));
        cursor.close();
    }

    private ContentValues cheeseWithName(String name) {
        final ContentValues values = new ContentValues();
        values.put(Cheese.COLUMN_NAME, name);
        return values;
    }
}
