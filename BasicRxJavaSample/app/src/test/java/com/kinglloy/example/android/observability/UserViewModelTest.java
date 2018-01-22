package com.kinglloy.example.android.observability;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.kinglloy.example.android.observability.persistence.User;
import com.kinglloy.example.android.observability.ui.UserViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link UserViewModel}
 *
 * @author jinyalin
 * @since 2018/1/22.
 */

public class UserViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private UserDataSource mDataSource;

    @Captor
    private ArgumentCaptor<User> mUserArgumentCaptor;

    private UserViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mViewModel = new UserViewModel(mDataSource);
    }

    @Test
    public void getUserName_whenNoUserSaved() throws InterruptedException {
        // Given that the UserDataSource returns an empty list of users
        when(mDataSource.getUser()).thenReturn(Flowable.empty());

        // When getting the username
        mViewModel.getUserName()
                .test()
                // The username is empty
                .assertNoValues();
    }

    @Test
    public void getUserName_whenUserSaved() throws InterruptedException {
        // Given that the UserDataSource returns a user
        User user = new User("user name");
        when(mDataSource.getUser()).thenReturn(Flowable.just(user));

        // When getting the username
        mViewModel.getUserName()
                .test()
                // The correct username is emitted
                .assertValue("user name");
    }

    @Test
    public void updateUserName_updatesNameInDataSource() {
        // When updating the username
        mViewModel.updateUserName("new user name")
                .test()
                .assertComplete();

        // The user name is updated in the data source
        verify(mDataSource).insertOrUpdateUser(mUserArgumentCaptor.capture());
        assertThat(mUserArgumentCaptor.getValue().getUserName(), is("new user name"));
    }
}
