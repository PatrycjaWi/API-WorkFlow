package com.johnBryce.demo.layer2.auth;

import com.johnBryce.demo.beans.Client;
import com.johnBryce.demo.beans.Restaurant;
import com.johnBryce.demo.beans.UserIdAndPassword;
import com.johnBryce.demo.enums.Roles;
import com.johnBryce.demo.layer1.db.Database;
import com.johnBryce.demo.layer2.DbObject;
import com.johnBryce.demo.layer2.userManagement.ClientManagement;
import com.johnBryce.demo.layer2.userManagement.RestaurantManagement;

public class Register {

	private static long counter = 1000000;

	Database db = DbObject.getDB();
	ClientManagement clientManagement = new ClientManagement();
	RestaurantManagement restaurantManagement = new RestaurantManagement();

	public String register(String username, String password, Client client) throws Exception {
		if (!db.getUsersLoginData().containsKey(username)) {
			counter++;
			client.setId(counter);
			client.setRole(Roles.CLIENT);

			db.getUsersLoginData().put(username, new UserIdAndPassword(client.getId(), password));

			clientManagement.addClient(client);

			return "client was added";
		} else {
			throw new Exception("username " + username + " already exists");
		}
	}

	public String register(String username, String password, Restaurant restaurant) throws Exception {
		if (!db.getUsersLoginData().containsKey(username)) {
			counter++;
			restaurant.setId(counter);
			restaurant.setRole(Roles.RESTAURANT);

			db.getUsersLoginData().put(username, new UserIdAndPassword(restaurant.getId(), password));

			restaurantManagement.addRestaurant(restaurant);

			return "restaurant was added";
		} else {
			throw new Exception("username " + username + " already exists");
		}
	}

}
