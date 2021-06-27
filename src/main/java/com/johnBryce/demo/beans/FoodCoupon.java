package com.johnBryce.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodCoupon {
	private long id;
	private String name;
	private String info;
	private long ownerRestaurantId;

}
