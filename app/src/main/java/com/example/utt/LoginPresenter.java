package com.example.utt;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.utt.models.Student;
import com.example.utt.models.User;

import java.util.ArrayList;

public class LoginPresenter implements LoginModel.Presenter {
	private final LoginView view;
	private LoginModel model;


	public LoginPresenter(LoginView view) {
		this.view = view;
		this.model = new LoginModel();
	}	

  // For testing with mockito we need dependency injection with the model
  public LoginPresenter(LoginView view, LoginModel model){
    this.view = view;
    this.model = model;
  }


	public void query(String uname, String pword, View v) {
		if(v != null) {
			model.queryIsUser(uname, pword, this);
		}
	}

	public void cookieQuery(String userID, View v) {
		if(v != null) {
			model.queryUserByID(userID, this);
		}
	}

	@Override 
	public void onSuccess(String userID, String uname, AccountType accountType) {
		// Write their data to their local storage
		if (view instanceof Fragment) {
			Fragment viewFrag = (Fragment) view;
			setCookie(viewFrag.getContext(), userID);

			view.makeSnackbar("Welcome Back, " + uname);
			view.collapseKeyboard();

			if (accountType == AccountType.STUDENT) {
				Student.login(new Student(userID, uname, ""), new ArrayList<>());
				view.goToStudentHome();
			}
			else {
				User.login(new User(uname, ""));
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
		CookieLogin.setUserId(context, studentID);
	}

	public String getCookie(Context context) {
		return CookieLogin.getUserId(context);
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











