package com.kinglloy.android.persistence.migrations;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link UserPresenter}
 *
 * @author jinyalin
 * @since 2018/2/2.
 */

public class UserPresenterTest {
    @Mock
    private UserRepository mUserRepository;

    @Mock
    private UserView mView;

    @Captor
    private ArgumentCaptor<UpdateUserCallback> mUpdateUserCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<LoadUserCallback> mLoadUserCallbackArgumentCaptor;

    private UserPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mPresenter = new UserPresenter(mUserRepository, mView);
    }

    @Test
    public void updateUserName_updatesUserNameInRepo() {
        // When updating the username
        mPresenter.updateUserName("user name");

        // The userName is updated in the repository
        verify(mUserRepository).updateUserName(eq("user name"), any(UpdateUserCallback.class));
    }

    @Test
    public void updateUserName_updatesView() {
        // When updating the username
        mPresenter.updateUserName("user name");

        // Callback is captured and invoked
        verify(mUserRepository).updateUserName(eq("user name"),
                mUpdateUserCallbackArgumentCaptor.capture());
        User user = new User("user name");
        mUpdateUserCallbackArgumentCaptor.getValue().onUserUpdated(user);

        // Verify that the username is set in the view
        verify(mView).showUserName("user name");
    }

    @Test
    public void start_getsUserFromRepository() {
        // When calling start
        mPresenter.start();

        //Verify that the user is requested from repository
        verify(mUserRepository).getUser(any(LoadUserCallback.class));
    }

    @Test
    public void onUserLoaded_updatesView() {
        //Given that start is called
        mPresenter.start();
        // Callback is captured
        verify(mUserRepository).getUser(mLoadUserCallbackArgumentCaptor.capture());

        // When the user is loaded
        User user = new User("user name");
        mLoadUserCallbackArgumentCaptor.getValue().onUserLoaded(user);

        // The view is updated with the correct user name
        verify(mView).showUserName("user name");
    }

    @Test
    public void onDataNotAvailable_updatesView() {
        // Given that start is called
        mPresenter.start();
        // Callback is captured
        verify(mUserRepository).getUser(mLoadUserCallbackArgumentCaptor.capture());

        // When the data is not available
        mLoadUserCallbackArgumentCaptor.getValue().onDataNotAvailable();

        // The view is updated
        verify(mView).hideUserName();
    }
}
