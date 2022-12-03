package com.example.utt;

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

	public void setCookie(int studentID) {
		if (view instanceof Fragment) {
			Fragment viewFrag = (Fragment) view;
			Integer Id = (Integer) studentID;
			CookieLogin.setUserName(viewFrag.getContext(), Id.toString());
		}
	}

	interface LoginView {
		public void makeSnackbar(String message);
		public void goToAdminHome();
		public void goToStudentHome();
		public void goToSignUp();
	}

	public void onDestroy() {
		model = null;
	}
}






































