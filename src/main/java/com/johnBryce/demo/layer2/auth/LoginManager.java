package com.johnBryce.demo.layer2.auth;

import java.util.UUID;

import com.johnBryce.demo.layer1.db.Database;
import com.johnBryce.demo.layer2.DbObject;

public class LoginManager {

	Database db = DbObject.getDB();

	public String login(String username, String password) throws Exception {
		if (db.getUsersLoginData().containsKey(username)
				&& db.getUsersLoginData().get(username).getPassword().equals(password)) {

			String token = "";
			do {
				token = UUID.randomUUID().toString();
			} while (db.getLoggedUsers().containsKey(token));

			db.getLoggedUsers().put(token, db.getUsersLoginData().get(username).getId());

			return token;
		} else {
			throw new Exception("invalid username or password");
		}
	}

	public void logout(String token) {
		db.getLoggedUsers().remove(token);
	}
}
