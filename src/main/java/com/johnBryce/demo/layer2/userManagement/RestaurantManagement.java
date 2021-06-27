package com.johnBryce.demo.layer2.userManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.johnBryce.demo.beans.FoodCoupon;
import com.johnBryce.demo.beans.Restaurant;
import com.johnBryce.demo.layer1.db.Database;
import com.johnBryce.demo.layer2.DbObject;
import com.johnBryce.demo.layer2.auth.RestaurantAuth;

public class RestaurantManagement {

	private static long couponCounter = 1000000;

	RestaurantAuth rAuth = new RestaurantAuth();
	Database db = DbObject.getDB();

	public String addCoupon(long id, FoodCoupon coupon) throws Exception {
		Map<Long, FoodCoupon> coupons = db.getCoupons();

		for (Map.Entry<Long, FoodCoupon> entry : coupons.entrySet()) {
			if (entry.getValue().getOwnerRestaurantId() == coupon.getOwnerRestaurantId()
					&& entry.getValue().getName().equals(coupon.getName())) {
				throw new Exception(
						"coupon with name: " + coupon.getName() + " already exist for restaurant with id: " + id);
			}
		}

		couponCounter++;
		coupon.setId(couponCounter);

		db.getCoupons().put(coupon.getId(), coupon);

		ArrayList<Long> resCouponsIds = db.getResCoupons().get(id);

		if (resCouponsIds == null) {
			resCouponsIds = new ArrayList<Long>();
		}

		resCouponsIds.add(coupon.getId());

		db.getResCoupons().put(Long.valueOf(id), resCouponsIds);

		return "coupon added";

	}

	public String removeCoupon(long id, long couponId) throws Exception {
		if (db.getCoupons().containsKey(couponId)) {
			db.getCoupons().remove(couponId);

			ArrayList<Long> resCoupons = db.getResCoupons().get(id);
			resCoupons.remove(Long.valueOf(couponId));

			return "coupon removed";
		} else {
			throw new Exception("coupon with id: " + couponId + " does not exist");
		}
	}

	public List<FoodCoupon> getAllRestaurantCoupons(long id) {
		List<Long> restCoupons = db.getResCoupons().get(id);

		if (restCoupons == null) {
			restCoupons = new ArrayList<Long>();
		}

		List<FoodCoupon> coupons = new ArrayList<FoodCoupon>();

		for (Map.Entry<Long, FoodCoupon> entry : db.getCoupons().entrySet()) {
			if (restCoupons.contains(entry.getKey())) {
				coupons.add(entry.getValue());
			}
		}

		return coupons;
	}

	public Restaurant getRestaurantById(long id) throws Exception {
		Restaurant restaurant = db.getRestaurantsData().get(id);
		if (restaurant != null) {
			return restaurant;
		} else {
			throw new Exception("restaurant does not exists");
		}
	}

	public String addRestaurant(Restaurant restaurant) throws Exception {
		if (db.getRestaurantsData().get(restaurant.getId()) == null) {
			db.getRestaurantsData().put(restaurant.getId(), restaurant);
			return "restaurant added";
		} else {
			throw new Exception("restaurant already exist");
		}
	}

	public String updateRestaurant(Restaurant restaurant) throws Exception {
		Restaurant oldRestaurant = db.getRestaurantsData().get(restaurant.getId());

		if (oldRestaurant != null) {
			oldRestaurant.setName(restaurant.getName());
			db.getRestaurantsData().put(oldRestaurant.getId(), oldRestaurant);

			return "restaurant updated";
		} else {
			throw new Exception("server error");
		}
	}

	public String removeRestaurant(Restaurant restaurant) throws Exception {
		if (db.getRestaurantsData().remove(restaurant.getId(), restaurant)) {
			List<Long> restCoupons = db.getResCoupons().get(restaurant.getId());

			if (restCoupons != null) {
				for (Long couponId : restCoupons) {
					db.getCoupons().remove(couponId);
				}
			}

			db.getResCoupons().remove(restaurant.getId());

			db.getUsersLoginData().remove(restaurant.getUsername());

			return "restaurant removed";
		} else {
			throw new Exception("restaurant does not exists");
		}
	}

	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		Map<Long, Restaurant> restaurantsData = db.getRestaurantsData();

		for (Map.Entry<Long, Restaurant> entry : restaurantsData.entrySet()) {
			restaurants.add(entry.getValue());
		}

		return restaurants;
	}

	public Restaurant validateToken(String token) throws Exception {
		return rAuth.validateToken(token);
	}

}
