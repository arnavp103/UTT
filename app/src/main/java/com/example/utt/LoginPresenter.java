package com.example.utt;


public class LoginPresenter implements LoginModel.LoginPresenter {
	private LoginView view;
	private LoginModel model;

	
	public LoginPresenter(LoginView view) {
		this.view = view;
		this.model = new DatabaseModel();
	}	


	public void query(String uname, String pword) {
		if(view != null) {
			SharedMethods.collapseKeyboard(view);
			model.queryIsUser(uname, pword, this);
		}
	}


	@Override 
	public void onSuccess(String result) {
		if(result) {
			makeSnackbar("Welcome Back "+ result);
		} else {
			makeSnackbar("Invalid Username or Password");
		}	

	}

	public void setCookie(int studentID) {
			cookieLogin.setUserName();
	}

	public interface LoginView {
		public void makeSnackbar(String message);
	}


	@Override
	public void onDestroy() {
		view = null;
	}

}






































