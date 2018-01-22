package com.kinglloy.example.android.observability;

import android.content.Context;

import com.kinglloy.example.android.observability.persistence.LocalUserDataSource;
import com.kinglloy.example.android.observability.persistence.UsersDatabase;
import com.kinglloy.example.android.observability.ui.ViewModelFactory;

/**
 * Enables injection of data sources.
 *
 * @author jinyalin
 * @since 2018/1/21.
 */

public class Injection {
    public static UserDataSource provideUserDataSource(Context context) {
        UsersDatabase database = UsersDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
