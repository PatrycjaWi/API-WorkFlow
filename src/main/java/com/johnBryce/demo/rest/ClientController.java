package com.johnBryce.demo.rest;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnBryce.demo.beans.Client;
import com.johnBryce.demo.beans.FoodCoupon;
import com.johnBryce.demo.enums.Roles;
import com.johnBryce.demo.layer2.userManagement.ClientManagement;
import com.johnBryce.demo.utils.ResponseUtil;

@RestController
@RequestMapping("/client")
public class ClientController {

	ClientManagement cManagement;

	@PostConstruct
	private void init() {
		cManagement = new ClientManagement();
	}

	@PostMapping(value = "/buy/{couponId}")
	public ResponseEntity<?> buyCoupon(@RequestHeader("token") String token, @PathVariable("couponId") long couponId) {
		try {
			Client validatedClient = cManagement.validateToken(token);
			if (validatedClient.getRole() == Roles.CLIENT) {
				try {
					String response = cManagement.buyCoupon(validatedClient.getId(), couponId);

					return ResponseUtil.response(HttpStatus.OK, response);
				} catch (Exception e) {
					return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only client can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(value = "/use/{couponId}")
	public ResponseEntity<?> useCoupon(@RequestHeader("token") String token, @PathVariable("couponId") long couponId) {
		try {
			Client validatedClient = cManagement.validateToken(token);
			if (validatedClient.getRole() == Roles.CLIENT) {
				try {
					String response = cManagement.useCoupon(validatedClient.getId(), couponId);

					return ResponseUtil.response(HttpStatus.OK, response);
				} catch (Exception e) {
					return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only client can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(value = "/update")
	public ResponseEntity<?> updateClient(@RequestHeader("token") String token, @RequestBody Client client) {
		try {
			Client validatedClient = cManagement.validateToken(token);
			client.setId(validatedClient.getId());
			if (validatedClient.getRole() == Roles.CLIENT) {
				try {
					String response = cManagement.updateClient(client);

					return ResponseUtil.response(HttpStatus.OK, response);
				} catch (Exception e) {
					return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
				}
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only client can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/myInfo")
	public ResponseEntity<?> getClientInfo(@RequestHeader("token") String token) {
		try {
			Client validatedClient = cManagement.validateToken(token);
			if (validatedClient.getRole() == Roles.CLIENT) {
				return ResponseUtil.response(HttpStatus.OK, validatedClient);
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only client can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/myCoupons")
	public ResponseEntity<?> getMyCoupons(@RequestHeader("token") String token) {
		try {
			Client validatedClient = cManagement.validateToken(token);
			if (validatedClient.getRole() == Roles.CLIENT) {
				List<FoodCoupon> coupons = cManagement.getMyCoupons(validatedClient.getId());

				return ResponseUtil.response(HttpStatus.OK, coupons);
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only client can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/allCoupons")
	public ResponseEntity<?> getAllCoupons(@RequestHeader("token") String token) {
		try {
			Client validatedClient = cManagement.validateToken(token);
			if (validatedClient.getRole() == Roles.CLIENT) {
				List<FoodCoupon> coupons = cManagement.getAllCoupons();

				return ResponseUtil.response(HttpStatus.OK, coupons);
			} else {
				return ResponseUtil.response(HttpStatus.NOT_ACCEPTABLE, "only client can use this api");
			}
		} catch (Exception e) {
			return ResponseUtil.response(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
