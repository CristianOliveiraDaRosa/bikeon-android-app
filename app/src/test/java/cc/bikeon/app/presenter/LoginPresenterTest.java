package cc.bikeon.app.presenter;

import android.app.Activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.annotation.Config;

import cc.bikeon.app.R;
import cc.bikeon.app.account.LoginRequester;
import cc.bikeon.app.account.LoginStrategy;
import cc.bikeon.app.account.LoginStrategyChooser;
import cc.bikeon.app.account.session.SessionAccount;
import cc.bikeon.app.account.session.SessionManager;
import cc.bikeon.app.account.session.SessionProvider;
import cc.bikeon.app.views.LoginView;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link LoginPresenter}
 * Created by cristianoliveira on 30/06/15.
 */
@RunWith(MockitoJUnitRunner.class)
@Config(manifest = Config.NONE)
public class LoginPresenterTest {

    @Mock
    LoginView view;
    @Mock
    SessionManager sessionManager;
    @InjectMocks
    LoginPresenter loginPresenter;

    @Test
    public void itShouldRequestLoginUsingItSelfAsCallback() {
        // given
        Activity currentActivity = mock(Activity.class);
        LoginStrategyChooser loginStrategyChooser = mock(LoginStrategyChooser.class);
        LoginStrategy loginStrategy = LoginStrategy.BIKEON;
        LoginRequester loginRequester = mock(LoginRequester.class);

        given(loginStrategyChooser.get(loginStrategy, currentActivity))
                .willReturn(loginRequester);

        // when
        loginPresenter.requestLogin(loginStrategyChooser, loginStrategy, currentActivity);

        // then
        verify(loginRequester).doLogin(loginPresenter);
    }

    @Test
    public void itShouldShowErrorMessageWhenLoginRequestRespondNull() {
        // when
        loginPresenter.onLoginSuccess(null);

        // then
        verify(view).showError(R.string.message_error_login);
    }


    @Test
    public void itShouldSaveSessionProviderWhenLoginRequestIsSuccessSessionManager() {
        // given
        SessionAccount sessionAccount = mock(SessionAccount.class);
        given(sessionAccount.getProvider()).willReturn(SessionProvider.BIKEON);

        // when
        loginPresenter.onLoginSuccess(sessionAccount);

        // then
        verify(sessionManager).saveCurrentProvider(sessionAccount);
    }

    @Test
    public void itShouldNotGoToMainWhenHasNoActiveSession() {
        // given
        SessionAccount notActiveSession = mock(SessionAccount.class);
        given(notActiveSession.isActive()).willReturn(false);

        given(sessionManager.getCurrentSession()).willReturn(notActiveSession);

        // when
        loginPresenter.verifySession();

        // then
        verify(view, never()).gotoMainActivity();
    }

    @Test
    public void itShouldGoToMainWhenHasActiveSession() {
        // given
        SessionAccount activeSession = mock(SessionAccount.class);
        given(activeSession.isActive()).willReturn(true);

        given(sessionManager.getCurrentSession()).willReturn(activeSession);

        // when
        loginPresenter.verifySession();

        // then
        verify(view).gotoMainActivity();
    }

    @Test
    public void itShouldGoToMainWhenLoginRequestIsSuccess() {
        // given
        SessionAccount sessionAccount = mock(SessionAccount.class);
        given(sessionAccount.getProvider()).willReturn(SessionProvider.BIKEON);

        // when
        loginPresenter.onLoginSuccess(sessionAccount);

        // then
        verify(view).gotoMainActivity();
    }

    @Test
    public void itShouldShowErrorMessageOnViewWhenLoginRequestFail() {
        // given
        String someError = "Some message error";

        // when
        loginPresenter.onLoginError(someError);

        // then
        verify(view).showError(R.string.message_error_login);
    }

    @Test
    public void itShouldNotRedirectToMainIfHasNoSessionActive() {
        // given
        SessionAccount closedSession = mock(SessionAccount.class);
        given(closedSession.isActive()).willReturn(false);
        given(sessionManager.getCurrentSession()).willReturn(closedSession);

        // when
        loginPresenter.verifySession();

        // then
        verify(view, never()).gotoMainActivity();

    }

    @Test
    public void itShouldRedirectToMainIfHasSessionActive() {
        // given
        SessionAccount activeSession = mock(SessionAccount.class);
        given(activeSession.isActive()).willReturn(true);
        given(sessionManager.getCurrentSession()).willReturn(activeSession);

        // when
        loginPresenter.verifySession();

        // then
        verify(view).gotoMainActivity();

    }
}
