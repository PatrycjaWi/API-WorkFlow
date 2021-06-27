package com.johnBryce.demo.layer2.auth;

import com.johnBryce.demo.beans.Restaurant;
import com.johnBryce.demo.layer1.db.Database;
import com.johnBryce.demo.layer2.DbObject;

public class RestaurantAuth {

	Database db = DbObject.getDB();

	public Restaurant validateToken(String token) throws Exception {
		if (db.getLoggedUsers().containsKey(token)) {
			long id = db.getLoggedUsers().get(token);
			Restaurant restaurant = db.getRestaurantsData().get(id);
			if (restaurant != null) {
				return restaurant;
			} else {
				throw new Exception("token is invalid");
			}
		} else {
			throw new Exception("token is invalid");
		}
	}
}
