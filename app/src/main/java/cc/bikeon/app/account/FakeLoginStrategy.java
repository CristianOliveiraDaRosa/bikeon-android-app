package cc.bikeon.app.account;

/**
 * Created by cristianoliveira on 23/05/15.
 * <p/>
 * CLASS to use during tests.
 */
public class FakeLoginStrategy implements LoginStrategy {
    @Override
    public void doLogin(LoginCallback callback) {
        callback.onLoginSuccess(null);
    }
}
