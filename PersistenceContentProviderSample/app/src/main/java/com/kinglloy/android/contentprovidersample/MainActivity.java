package com.kinglloy.android.contentprovidersample;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kinglloy.android.contentprovidersample.data.Cheese;
import com.kinglloy.android.contentprovidersample.provider.SampleContentProvider;

/**
 * Not very relevant to Room. This just shows data from {@link SampleContentProvider}.
 * <p>
 * <p>Since the data is exposed through the ContentProvider, other apps can read and write the
 * content in a similar manner to this.</p>
 */
public class MainActivity extends AppCompatActivity {

    private static final int LOADER_CHEESES = 1;

    private CheeseAdapter mCheeseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));
        mCheeseAdapter = new CheeseAdapter();
        list.setAdapter(mCheeseAdapter);

        getSupportLoaderManager().initLoader(LOADER_CHEESES, null, mLoaderCallbacks);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case LOADER_CHEESES:
                            return new CursorLoader(getApplicationContext(),
                                    SampleContentProvider.URI_CHEESE,
                                    new String[]{Cheese.COLUMN_NAME},
                                    null, null, null);
                        default:
                            throw new IllegalArgumentException();
                    }
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    switch (loader.getId()) {
                        case LOADER_CHEESES:
                            mCheeseAdapter.setCheeses(data);
                            break;
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    switch (loader.getId()) {
                        case LOADER_CHEESES:
                            mCheeseAdapter.setCheeses(null);
                            break;
                    }
                }
            };

    private static class CheeseAdapter extends RecyclerView.Adapter<CheeseAdapter.ViewHolder> {
        private Cursor mCursor;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (mCursor.moveToPosition(position)) {
                holder.mText.setText(mCursor.getString(
                        mCursor.getColumnIndexOrThrow(Cheese.COLUMN_NAME)));
            }
        }

        @Override
        public int getItemCount() {
            return mCursor == null ? 0 : mCursor.getCount();
        }

        void setCheeses(Cursor cursor) {
            mCursor = cursor;
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView mText;

            ViewHolder(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext()).inflate(
                        android.R.layout.simple_list_item_1, parent, false));
                mText = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
