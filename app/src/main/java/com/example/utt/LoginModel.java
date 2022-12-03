package com.example.utt;


public class LoginModel extends BaseModel {

	public LoginModel(){

	}

	
	public void queryIsUser(String uname, String pword, LoginPresenter presenter) {
		super.queryStudentsByName(uname).addListener(onSuccess(val -> {
		if(val != null && val.pword == pword) {
			presenter.onSuccess(val.uname);
		}}).onFailure({throw new DatabaseReferenceError});
	}


	public interface LoginPresenter {

		void onSuccess(String name);

		void onFailure();

	}

}

