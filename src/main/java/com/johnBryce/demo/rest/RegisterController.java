package com.johnBryce.demo.rest;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.johnBryce.demo.beans.Client;
import com.johnBryce.demo.beans.Restaurant;
import com.johnBryce.demo.enums.Roles;
import com.johnBryce.demo.layer2.auth.Register;
import com.johnBryce.demo.layer2.userManagement.ClientManagement;
import com.johnBryce.demo.utils.ResponseUtil;

@RestController
public class RegisterController {

	Register rService;
	ClientManagement cManagement;

	@PostConstruct
	private void init() {
		rService = new Register();
		cManagement = new ClientManagement();
	}

	@PostMapping(value = "/register/client", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@RequestHeader("user") String username, @RequestHeader("pass") String password,
			@RequestBody Client client) {
		try {
			client.setUsername(username);
			client.setPassword(password);
			String response = rService.register(username, password, client);

			return ResponseUtil.response(HttpStatus.CREATED, response);
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping(value = "/register/restaurant", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerRestaurant(@RequestHeader("token") String token,
			@RequestHeader("user") String username, @RequestHeader("pass") String password,
			@RequestBody Restaurant restaurant) {
		try {
			Client adminUser = cManagement.validateToken(token);

			if (adminUser.getRole() == Roles.ADMIN) {
				try {
					restaurant.setUsername(username);
					restaurant.setPassword(password);
					String response = rService.register(username, password, restaurant);

					return ResponseUtil.response(HttpStatus.CREATED, response);
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
