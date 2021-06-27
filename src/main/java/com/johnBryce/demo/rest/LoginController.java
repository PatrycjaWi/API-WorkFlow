package com.johnBryce.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.johnBryce.demo.layer2.auth.LoginManager;
import com.johnBryce.demo.utils.ResponseUtil;

@RestController
public class LoginController {

	LoginManager lManager = new LoginManager();

	@GetMapping(value = "/login")
	public ResponseEntity<?> login(@RequestHeader("user") String username, @RequestHeader("pass") String password) {
		try {
			String token = lManager.login(username, password);

			return ResponseUtil.response(HttpStatus.OK, token);
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(value = "/logout/{token}")
	public ResponseEntity<?> logoutByPath(@PathVariable("token") String token) {
		lManager.logout(token);

		return ResponseUtil.response(HttpStatus.OK, "user logged out");
	}

	@GetMapping(value = "/logout")
	public ResponseEntity<?> logoutByHeader(@RequestHeader("token") String token) {
		lManager.logout(token);

		return ResponseUtil.response(HttpStatus.OK, "user logged out");
	}
}
