package com.johnBryce.demo.beans;

import java.util.List;

import com.johnBryce.demo.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
	private long id;
	private String username;
	private String password;
	private Roles role;
	private String name;
	private List<FoodCoupon> coupons;
}
