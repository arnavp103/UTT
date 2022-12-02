package com.example.utt;


public class LoginPresenter implements Presenter {
	private LoginView view;
	private DatabaseModel model;

	
	public LoginPresenter(LoginView view) {
		this.view = view;
		this.model = new DatabaseModel();
	}	




	public void query(String uname, String pword) {
		model.queryIsUser(uname, pword, this);	
	}

	@Override 
	public onFinished()







	public interface LoginView {
		public makeSnackbar(String message);
		public collapseKeyboard();
		
	}
	@Override
	public void onDestroy() {
		view = null;
	}

}






































