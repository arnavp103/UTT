package com.example.utt;


import androidx.annotation.Nullable;

import com.example.utt.database.DatabaseHandler;
import com.example.utt.models.Listener;
import com.example.utt.models.User;

import java.util.List;

public class LoginModel extends BaseModel {


	public interface OnReadyListener {
		public void onReady();
	}

	public interface Presenter {
		enum AccountType { ADMIN, STUDENT; }

		void onSuccess(String userID, String uname, AccountType accessType);

		void onFailure();

	}

	public LoginModel(){}

	// for cookieLogin
	public void queryUserByID(String userID, Presenter presenter) {
		Listener<User> callback = new Listener<User>() {
			@Override
			public void onSuccess(String data, @Nullable List<User> objectModel) {
				assert objectModel != null;
				User user = objectModel.get(0);
				Presenter.AccountType accessType = (user.isStudent()) ?
						Presenter.AccountType.STUDENT :
						Presenter.AccountType.ADMIN;

				presenter.onSuccess(user.getId(), user.getEmail(), accessType);
			}

			@Override
			public void onFailure(String data) {
				presenter.onFailure();
			}

			@Override
			public void onComplete(String data) {}
		};
		DatabaseHandler.getUser(userID, callback);
	}

	// for submit buttons
	public void queryIsUser(String uname, String pword, Presenter presenter) {
		Listener<User> callback = new Listener<User>() {
			@Override
			public void onSuccess(String data, @Nullable List<User> objectModel) {
				assert objectModel != null;
				User user = objectModel.get(0);
				Presenter.AccountType accessType = (user.isStudent()) ?
						Presenter.AccountType.STUDENT :
						Presenter.AccountType.ADMIN;

				presenter.onSuccess(user.getId(), user.getEmail(), accessType);
			}

			@Override
			public void onFailure(String data) {
				presenter.onFailure();
			}

			@Override
			public void onComplete(String data) {}
		};
		DatabaseHandler.getUser(uname, pword, callback);
	}

	public static void addOnReadyListener(LoginModel.OnReadyListener callback) {
		DatabaseHandler.addOnReadyListener(new DatabaseHandler.OnReadyListener() {
			@Override
			public void onReady() {
				callback.onReady();
			}
		});
	}
}