package com.johnBryce.demo.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.johnBryce.demo.beans.other.Test;

@RestController
@RequestMapping("/tests")
public class SimpleRest {
	Map<Integer, Test> tests = new HashMap<Integer, Test>();

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getByPath(@PathVariable("id") int testId) {
		if (tests.containsKey(testId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body("{\"body\": \"test name is: " + tests.get(testId).getName() + "\", \"status\": 200}");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"body\": \"test was not found\", \"status\": 404}");
		}
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getByQuery(@RequestParam("id") int testId, @RequestParam("name") String testName) {
		if (tests.containsKey(testId)) {
			Test t1 = new Test(testId, testName);

			if (tests.get(testId).equals(t1)) {
				return ResponseEntity.status(HttpStatus.OK)
						.body("{\"body\": \"test name is: " + tests.get(testId).getName() + "\", \"status\": 200}");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("{\"body\": \"test with name: '" + testName + "' was not found\", \"status\": 404}");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"body\": \"test was not found\", \"status\": 404}");
		}

	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> post(@RequestBody Test test) {
		System.out.println("post triggered");
		if (!tests.containsKey(test.getId())) {
			tests.put(Integer.valueOf(test.getId()), test);

			return ResponseEntity.status(HttpStatus.OK).body("{\"body\": \"test was added\", \"status\": 200}");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"body\": \"test already exists\", \"status\": 404}");
		}
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putByPath(@PathVariable int id, @RequestBody Test test) {
		if (test.getId() == id) {
			if (tests.containsKey(id)) {
				tests.put(id, test);

				return ResponseEntity.status(HttpStatus.OK).body("{\"body\": \"test was updated\", \"status\": 200}");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("{\"body\": \"test was not found\", \"status\": 404}");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"body\": \"id and test.id does not match\", \"status\": 400}");
		}
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> putByQuery(@RequestParam("id") int id, @RequestBody Test test) {
		if (test.getId() == id) {
			if (tests.containsKey(id)) {
				tests.put(id, test);

				return ResponseEntity.status(HttpStatus.OK).body("{\"body\": \"test was updated\", \"status\": 200}");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("{\"body\": \"test was not found\", \"status\": 404}");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"body\": \"id and test.id does not match\", \"status\": 400}");
		}
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteByPath(@PathVariable("id") int testId, @RequestBody Test test) {
		if (tests.containsKey(testId)) {
			tests.remove(test.getId());

			return ResponseEntity.status(HttpStatus.OK).body("{\"body\": \"test was deleted\", \"status\": 200}");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"body\": \"test was not found\", \"status\": 404}");
		}
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteByQuery(@RequestParam("id") int testId, @RequestBody Test test) {
		if (tests.containsKey(testId)) {
			tests.remove(test.getId());

			return ResponseEntity.status(HttpStatus.OK).body("{\"body\": \"test was deleted\", \"status\": 200}");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"body\": \"test was not found\", \"status\": 404}");
		}
	}

}
