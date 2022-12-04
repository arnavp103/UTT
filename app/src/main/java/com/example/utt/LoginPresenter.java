package com.example.utt;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.utt.models.Listener;
import com.example.utt.models.User;

import java.util.List;

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
			SharedMethods.collapseKeyboard(v.getContext());
			model.queryIsUser(uname, pword, this);
		}
	}


	@Override 
	public void onSuccess(String result) {

		// Write their data to their local storage
		if (view instanceof Fragment) {
			Fragment viewFrag = (Fragment) view;
			setCookie(viewFrag.getContext(), result);
		}
		// checkUserStatus(user.get(0), view);
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






































