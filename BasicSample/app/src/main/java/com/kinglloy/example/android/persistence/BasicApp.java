package com.kinglloy.example.android.persistence;

import android.app.Application;

import com.kinglloy.example.android.persistence.db.AppDatabase;

/**
 * @author jinyalin
 * @since 2018/1/15.
 */

public class BasicApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
