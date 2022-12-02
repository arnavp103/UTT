package com.example.utt;


public class LoginModel extends BaseModel {

	public LoginModel{
		super();
	}

	
	public void queryIsUser(String uname, String pword, LoginPresenter presenter) {
		super.queryStudentsByName(uname).onSuccess(val -> {
		if(val != null && val.password == pword) {
			presenter.onFinished(val.username);
		}}).onFailure({throw new DatabaseReferenceError});
	}


	public interface LoginPresenter {

		void onSuccess(String name);

		void onFailure();

	}

}

