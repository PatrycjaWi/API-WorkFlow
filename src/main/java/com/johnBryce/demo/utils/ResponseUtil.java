package com.johnBryce.demo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
	public static ResponseEntity<?> response(HttpStatus status, Object body) {
		return ResponseEntity.status(status).body(body);
	}
}
