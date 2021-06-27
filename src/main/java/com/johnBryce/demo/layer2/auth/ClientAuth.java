package com.johnBryce.demo.layer2.auth;

import com.johnBryce.demo.beans.Client;
import com.johnBryce.demo.layer1.db.Database;
import com.johnBryce.demo.layer2.DbObject;

public class ClientAuth {

	Database db = DbObject.getDB();

	public Client validateToken(String token) throws Exception {
		if (db.getLoggedUsers().containsKey(token)) {
			long id = db.getLoggedUsers().get(token);
			Client client = db.getClientsData().get(id);
			if (client != null) {
				return client;
			} else {
				throw new Exception("token is invalid");
			}
		} else {
			throw new Exception("token is invalid");
		}
	}
}
