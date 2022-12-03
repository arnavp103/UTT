package com.example.utt;

import android.content.Context;
import android.view.View;

public class LoginPresenter implements LoginModel.Presenter {
	private final LoginView view;
	private LoginModel model;

	// TODO Need to add navigation

	public LoginPresenter(LoginView view) {
		this.view = view;
		this.model = new LoginModel();
	}	


	public void query(String uname, String pword, View view) {
		if(view != null) {
			SharedMethods.collapseKeyboard(view.getContext());
			model.queryIsUser(uname, pword, this);
		}
	}


	@Override 
	public void onSuccess(String result) {
		view.makeSnackbar("Welcome Back, "+ result);

	}

	@Override
	public void onFailure(){
		view.makeSnackbar("Invalid Username or Password");
	}

	public void setCookie(Context context, String studentID) {
		CookieLogin.setUserName(context, studentID);
	}

	public String getCookie(Context context) {
		return CookieLogin.getUserName(context);
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






































