package com.example.utt;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

public class LoginPresenter implements LoginModel.Presenter {
	private final LoginView view;
	private LoginModel model;

	// TODO Need to add navigation

	public LoginPresenter(LoginView view) {
		this.view = view;
		this.model = new LoginModel();
	}	


	public void query(String uname, String pword, View v) {
		if(v != null) {
			model.queryIsUser(uname, pword, this);
		}
	}

	@Override 
	public void onSuccess(String userID, AccountType accountType) {
		// Write their data to their local storage
		if (view instanceof Fragment) {
			Fragment viewFrag = (Fragment) view;
			setCookie(viewFrag.getContext(), userID);
			// This can't be done unless we can get a username from userID
			// String username = ;
			// view.makeSnackbar("Welcome Back, " + username);
			view.makeSnackbar("Welcome Back, " + userID);
			view.collapseKeyboard();
			if (accountType == AccountType.STUDENT) {
				view.goToStudentHome();
			}
			else {
				view.goToAdminHome();
			}
		}
		// checkUserStatus(user.get(0), view);
	}

	@Override
	public void onFailure(){
		view.makeSnackbar("Invalid Username or Password");
		view.collapseKeyboard();
	}

	public void setCookie(Context context, String studentID) {
		CookieLogin.setUserName(context, studentID);
	}

	public String getCookie(Context context) {
		return CookieLogin.getUserName(context);
	}

	private void studentLogin() {
	}

	private void adminLogin() {

	}

	interface LoginView {
		public void makeSnackbar(String message);
		public void goToAdminHome();
		public void goToStudentHome();
		public void goToSignUp();
		public void collapseKeyboard();
	}

	public void onDestroy() {
		model = null;
	}
}





































