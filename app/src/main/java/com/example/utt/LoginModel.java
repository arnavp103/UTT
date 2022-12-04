package com.example.utt;


import android.util.Log;

import androidx.annotation.Nullable;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.models.Listener;
import com.example.utt.models.Student;
import com.example.utt.models.User;

import java.util.List;

public class LoginModel extends BaseModel {

	public interface Presenter {

		void onSuccess(String name);

		void onFailure();

	}

	public LoginModel(){

	}


//		super.queryStudentsByName(uname).addListener(onSuccess(val -> {
//		if(val != null && val.pword == pword) {
//			presenter.onSuccess(val.uname);
//		}}).onFailure(exception -> {throw new DatabaseReferenceError;});
//	}
	public void queryIsUser(String uname, String pword, Presenter presenter) {
		Listener<User> callback = new Listener<User>() {
			@Override
			public void onSuccess(String data, @Nullable List<User> objectModel) {
				presenter.onSuccess(objectModel.get(0).getId());
			}

			@Override
			public void onFailure(String data) {
				presenter.onFailure();
			}

			@Override
			public void onComplete(String data) {

			}
		};
		DatabaseHandler.getUser(uname, pword, callback);
	}


}