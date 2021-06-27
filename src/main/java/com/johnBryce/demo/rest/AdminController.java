package com.johnBryce.demo.rest;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.johnBryce.demo.beans.Client;
import com.johnBryce.demo.beans.Restaurant;
import com.johnBryce.demo.enums.Roles;
import com.johnBryce.demo.layer2.auth.Register;
import com.johnBryce.demo.layer2.userManagement.ClientManagement;
import com.johnBryce.demo.layer2.userManagement.RestaurantManagement;
import com.johnBryce.demo.utils.ResponseUtil;

@RestController
@RequestMapping("/admin")
public class AdminController {

	ClientManagement cManagement;
	RestaurantManagement rManagement;
	Register rService;

	@PostConstruct
	private void init() {
		cManagement = new ClientManagement();
		rManagement = new RestaurantManagement();
		rService = new Register();
	}

	@GetMapping(value = "/getClient")
	public ResponseEntity<?> getClientById(@RequestHeader("token") String token, @RequestParam("id") long id) {
		try {
			Client adminUser = cManagement.validateToken(token);
			if (adminUser.getRole() == Roles.ADMIN) {
				try {
					Client client = cManagement.getClientById(id);

					return ResponseUtil.response(HttpStatus.OK, client);
				} catch (Exception e) {
					return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only admin can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(value = "/getRestaurant")
	public ResponseEntity<?> getRestaurantById(@RequestHeader("token") String token, @RequestParam("id") long id) {
		try {
			Client adminUser = cManagement.validateToken(token);
			if (adminUser.getRole() == Roles.ADMIN) {

				try {
					Restaurant restaurant = rManagement.getRestaurantById(id);

					return ResponseUtil.response(HttpStatus.OK, restaurant);
				} catch (Exception e) {
					return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only admin can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(value = "/getAllClients")
	public ResponseEntity<?> getAllClients(@RequestHeader("token") String token) {
		try {
			Client adminUser = cManagement.validateToken(token);

			if (adminUser.getRole() == Roles.ADMIN) {
				try {
					List<Client> clients = cManagement.getAllClients();

					return ResponseUtil.response(HttpStatus.OK, clients);
				} catch (Exception e) {
					return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only admin can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(value = "/getAllRestaurants")
	public ResponseEntity<?> getAllRestaurants(@RequestHeader("token") String token) {
		try {
			Client adminUser = cManagement.validateToken(token);

			if (adminUser.getRole() == Roles.ADMIN) {
				try {
					List<Restaurant> restaurants = rManagement.getAllRestaurants();

					return ResponseUtil.response(HttpStatus.OK, restaurants);
				} catch (Exception e) {
					return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only admin can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(value = "/deleteClient")
	public ResponseEntity<?> deleteClient(@RequestHeader("token") String token, @RequestBody Client client) {
		try {
			Client adminUser = cManagement.validateToken(token);

			if (adminUser.getRole() == Roles.ADMIN) {
				try {
					String response = cManagement.removeClient(client);

					return ResponseUtil.response(HttpStatus.OK, response);
				} catch (Exception e) {
					return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only admin can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(value = "/deleteRestaurant")
	public ResponseEntity<?> deleteRestaurant(@RequestHeader("token") String token,
			@RequestBody Restaurant restaurant) {
		try {
			Client adminUser = cManagement.validateToken(token);

			if (adminUser.getRole() == Roles.ADMIN) {
				try {
					String response = rManagement.removeRestaurant(restaurant);

					return ResponseUtil.response(HttpStatus.OK, response);
				} catch (Exception e) {
					return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only admin can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
