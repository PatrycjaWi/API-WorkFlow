package com.johnBryce.demo.beans;

import com.johnBryce.demo.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
public class Client {
	private long id;
	private String username;
	private String password;
	private Roles role;
	private String firstName;
	private String lastName;
	private int age;
}
