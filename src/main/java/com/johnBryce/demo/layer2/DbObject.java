package com.johnBryce.demo.layer2;

import com.johnBryce.demo.layer1.db.Database;

public class DbObject {

	private static Database DB = new Database();

	public static Database getDB() {
		return DB;
	}
	
}
