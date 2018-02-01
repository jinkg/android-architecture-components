package com.kinglloy.android.persistence.migrations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Main screen of the app. Displays a user name and allows the option to update the user name.
 */
public class UserActivity extends AppCompatActivity implements UserView {
    private TextView mUserName;

    private EditText mUserNameInput;

    private Button mUpdateButton;

    private UserPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mUserName = findViewById(R.id.user_name);
        mUserNameInput = findViewById(R.id.user_name_input);
        mUpdateButton = findViewById(R.id.update_user);

        mUpdateButton.setOnClickListener(v -> {
            String userName = mUserNameInput.getText().toString();
            mPresenter.updateUserName(userName);
        });

        UserRepository userRepository = new UserRepository(new AppExecutors(),
                LocalUserDataSource.getInstance(getApplicationContext()));

        mPresenter = new UserPresenter(userRepository, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destory();
    }

    @Override
    public void showUserName(String userName) {
        mUserName.setVisibility(View.VISIBLE);
        mUserName.setText(userName);
    }

    @Override
    public void hideUserName() {
        mUserName.setVisibility(View.INVISIBLE);
    }
}
