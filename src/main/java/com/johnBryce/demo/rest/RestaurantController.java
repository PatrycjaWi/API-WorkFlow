package com.johnBryce.demo.rest;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.johnBryce.demo.beans.FoodCoupon;
import com.johnBryce.demo.beans.Restaurant;
import com.johnBryce.demo.enums.Roles;
import com.johnBryce.demo.layer2.userManagement.RestaurantManagement;
import com.johnBryce.demo.utils.ResponseUtil;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

	RestaurantManagement rManagement;

	@PostConstruct
	private void Init() {
		rManagement = new RestaurantManagement();
	}

	@PostMapping("/addCoupon")
	public ResponseEntity<?> addCoupon(@RequestHeader("token") String token, @RequestBody FoodCoupon coupon) {
		try {
			Restaurant validatedRestaurant = rManagement.validateToken(token);
			if (validatedRestaurant.getRole() == Roles.RESTAURANT) {
				coupon.setOwnerRestaurantId(validatedRestaurant.getId());

				String response = rManagement.addCoupon(validatedRestaurant.getId(), coupon);

				return ResponseUtil.response(HttpStatus.CREATED, response);
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only restaurant can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/removeCoupon")
	public ResponseEntity<?> removeCoupon(@RequestHeader("token") String token, @RequestParam("id") long couponId) {
		try {
			Restaurant validatedRestaurant = rManagement.validateToken(token);
			if (validatedRestaurant.getRole() == Roles.RESTAURANT) {
				String response = rManagement.removeCoupon(validatedRestaurant.getId(), couponId);

				return ResponseUtil.response(HttpStatus.OK, response);
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only restaurant can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/myCoupons")
	public ResponseEntity<?> getMyCoupons(@RequestHeader("token") String token) {
		try {
			Restaurant validatedRestaurant = rManagement.validateToken(token);
			if (validatedRestaurant.getRole() == Roles.RESTAURANT) {
				List<FoodCoupon> restCoupons = rManagement.getAllRestaurantCoupons(validatedRestaurant.getId());

				return ResponseUtil.response(HttpStatus.OK, restCoupons);
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only restaurant can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(value = "/update")
	public ResponseEntity<?> updateRestaurant(@RequestHeader("token") String token,
			@RequestBody Restaurant restaurant) {
		try {
			Restaurant validatedRestaurant = rManagement.validateToken(token);
			restaurant.setId(validatedRestaurant.getId());
			if (validatedRestaurant.getRole() == Roles.RESTAURANT) {
				try {
					String response = rManagement.updateRestaurant(restaurant);

					return ResponseUtil.response(HttpStatus.OK, response);
				} catch (Exception e) {
					return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only restaurant can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/myInfo")
	public ResponseEntity<?> getRestaurantInfo(@RequestHeader("token") String token) {
		try {
			Restaurant validatedRestaurant = rManagement.validateToken(token);
			if (validatedRestaurant.getRole() == Roles.RESTAURANT) {
				return ResponseUtil.response(HttpStatus.OK, validatedRestaurant);
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only restaurant can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
