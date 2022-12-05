package com.example.utt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.view.View;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


public class LoginPresenterTest {

    private class TestLoginPresenter extends LoginPresenter {
        public TestLoginPresenter(LoginView view, LoginModel model) {
            super(view, model);
        }

        @Override
        public void onSuccess(String userID, String uname, AccountType accountType) {

        }

        @Override
        public void onFailure() {

        }
    }

    @Test
    public void testQueryPass() {
        LoginPresenter.LoginView loginView = Mockito.mock(LoginPresenter.LoginView.class);
        View view = Mockito.mock(View.class);
        CookieLogin login = Mockito.mock(CookieLogin.class);
        LoginModel loginModel = Mockito.mock(LoginModel.class);
        Context context = Mockito.mock(Context.class);

        LoginPresenter presenter = new TestLoginPresenter(loginView, loginModel);

        presenter.cookieLogin = login;

        String username = "boba";
        String password = "tea";

        Mockito.doNothing().when(loginModel).queryIsUser(username, password, presenter);

        try {
            presenter.query(username, password, view);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testQueryFail() {
        LoginPresenter.LoginView loginView = Mockito.mock(LoginPresenter.LoginView.class);
        View view = Mockito.mock(View.class);
        CookieLogin login = Mockito.mock(CookieLogin.class);
        LoginModel loginModel = Mockito.mock(LoginModel.class);
        Context context = Mockito.mock(Context.class);

        LoginPresenter presenter = new LoginPresenter(loginView, loginModel);
        presenter.cookieLogin = login;

        String username = "boba";
        String password = "tea";

        Mockito.doThrow(new RuntimeException()).when(loginModel).queryIsUser(username, password, presenter);

        try {
            presenter.query(username, password, view);
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testQueryNoView() {
        LoginPresenter.LoginView loginView = Mockito.mock(LoginPresenter.LoginView.class);
        View view = Mockito.mock(View.class);
        CookieLogin login = Mockito.mock(CookieLogin.class);
        LoginModel loginModel = Mockito.mock(LoginModel.class);
        Context context = Mockito.mock(Context.class);

        LoginPresenter presenter = new LoginPresenter(loginView, loginModel);
        presenter.cookieLogin = login;

        String username = "boba";
        String password = "tea";

        Mockito.doNothing().when(loginModel).queryIsUser(username, password, presenter);

        try {
            presenter.query(username, password, null);
            assertTrue(true);
        } catch (RuntimeException e) {
            fail();
        }
    }

    @Test
    public void testCookieQueryPass() {
        LoginPresenter.LoginView loginView = Mockito.mock(LoginPresenter.LoginView.class);
        View view = Mockito.mock(View.class);
        CookieLogin login = Mockito.mock(CookieLogin.class);
        LoginModel loginModel = Mockito.mock(LoginModel.class);
        Context context = Mockito.mock(Context.class);

        LoginPresenter presenter = new TestLoginPresenter(loginView, loginModel);

        presenter.cookieLogin = login;

        String userId = "boba";

        Mockito.doNothing().when(loginModel).queryUserByID(userId, presenter);

        try {
            presenter.cookieQuery(userId, view);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testCookieQueryNoView() {
        LoginPresenter.LoginView loginView = Mockito.mock(LoginPresenter.LoginView.class);
        View view = Mockito.mock(View.class);
        CookieLogin login = Mockito.mock(CookieLogin.class);
        LoginModel loginModel = Mockito.mock(LoginModel.class);
        Context context = Mockito.mock(Context.class);

        LoginPresenter presenter = new TestLoginPresenter(loginView, loginModel);

        presenter.cookieLogin = login;

        String userId = "boba";

        Mockito.doNothing().when(loginModel).queryUserByID(userId, presenter);

        try {
            presenter.cookieQuery(userId, null);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testSetCookiePass() {
        LoginPresenter.LoginView loginView = Mockito.mock(LoginPresenter.LoginView.class);
        View view = Mockito.mock(View.class);
        CookieLogin login = Mockito.mock(CookieLogin.class);
        LoginModel loginModel = Mockito.mock(LoginModel.class);
        Context context = Mockito.mock(Context.class);

        LoginPresenter presenter = new TestLoginPresenter(loginView, loginModel);

        presenter.cookieLogin = login;

        String studentID = "boba";

        Mockito.doNothing().when(login).setUserId(context, studentID);

        try {
            presenter.setCookie(context, studentID);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }
    @Test
    public void testGetCookiePass() {
//        public String getCookie(Context context) {
//            return cookieLogin.getInstance().getUserId(context);
//        }
        LoginPresenter.LoginView loginView = Mockito.mock(LoginPresenter.LoginView.class);
        View view = Mockito.mock(View.class);
        CookieLogin login = Mockito.mock(CookieLogin.class);
        LoginModel loginModel = Mockito.mock(LoginModel.class);
        Context context = Mockito.mock(Context.class);

        LoginPresenter presenter = new TestLoginPresenter(loginView, loginModel);
        presenter.cookieLogin = login;

        when(login.getUserId(context)).thenReturn("cscb07");

        assertEquals("cscb07", presenter.getCookie(context));
    }

    @Test
    public void testOnDestroy() {
        LoginPresenter.LoginView loginView = Mockito.mock(LoginPresenter.LoginView.class);
        View view = Mockito.mock(View.class);
        CookieLogin login = Mockito.mock(CookieLogin.class);
        LoginModel loginModel = Mockito.mock(LoginModel.class);
        Context context = Mockito.mock(Context.class);

        LoginPresenter presenter = new TestLoginPresenter(loginView, loginModel);

        assertEquals(loginModel, presenter.model);
        presenter.onDestroy();
        assertEquals(null, presenter.model);
    }
}
