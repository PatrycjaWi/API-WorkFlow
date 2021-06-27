package com.johnBryce.demo.layer1.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.johnBryce.demo.beans.Client;
import com.johnBryce.demo.beans.FoodCoupon;
import com.johnBryce.demo.beans.Restaurant;
import com.johnBryce.demo.beans.UserIdAndPassword;
import com.johnBryce.demo.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Database {
	// key is username
	private Map<String, UserIdAndPassword> usersLoginData;

	// key is user id
	private Map<Long, Client> clientsData;

	// key is restaurant id
	private Map<Long, Restaurant> RestaurantsData;

	// key is token, value is user id
	private Map<String, Long> loggedUsers;

	// key is coupon id, value is coupon object
	private Map<Long, FoodCoupon> coupons;

	// key is restaurant id, value is list of coupons id
	private Map<Long, ArrayList<Long>> resCoupons;

	// key is client id, value is list of bought coupons objects
	private Map<Long, List<FoodCoupon>> clientCoupons;

	public Database() {
		usersLoginData = new HashMap<String, UserIdAndPassword>();
		usersLoginData.put("admin", new UserIdAndPassword(1, "admin"));

		clientsData = new HashMap<Long, Client>();
		clientsData.put(Long.valueOf(1), new Client((long) 1, "admin", "admin", Roles.ADMIN, "yehoraz", "levi", 27));

		RestaurantsData = new HashMap<Long, Restaurant>();

		loggedUsers = new HashMap<String, Long>();

		coupons = new HashMap<Long, FoodCoupon>();

		resCoupons = new HashMap<Long, ArrayList<Long>>();

		clientCoupons = new HashMap<Long, List<FoodCoupon>>();

	}

}
