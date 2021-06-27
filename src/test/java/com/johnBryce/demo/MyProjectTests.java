package com.johnBryce.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.johnBryce.demo.beans.Client;
import com.johnBryce.demo.beans.FoodCoupon;
import com.johnBryce.demo.beans.Restaurant;
import com.johnBryce.demo.enums.Roles;

import io.restassured.RestAssured;
//import jdk.jfr.internal.PrivateAccess;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class MyProjectTests {

	private static String token;
	private static long clientId=0;
	private static long restaurantId=0;
	private static long couponId = 0;

	private static int counter= 1;
	
	@BeforeEach
	private void setPort() {
		RestAssured.port = 8081;
		System.out.println(counter++ + "----------------------------------------------------------------------------------------");
	}


	@Test
	@Order(0)
	void registerClients() {
		
		Client firstClient = new Client(0,"","",null,"first1","last1",10);
		Client secondClient = new Client (0,"","",null,"first2","last2",20);
		Map<String, Object> userName = new HashMap<String, Object>();
		userName.put("user", "Pati");
		userName.put("pass", "123");	

		RestAssured.given().headers(userName).body(firstClient)
		.contentType(MediaType.APPLICATION_JSON_VALUE).given().post("/register/client")
		.then().statusCode(201).extract().asString();
		userName.put("user", "David");

		RestAssured.given().headers(userName).body(secondClient)
		.contentType(MediaType.APPLICATION_JSON_VALUE).given().post("/register/client")
		.then().statusCode(201).extract().asString();
		System.out.println(secondClient);
		System.out.println(firstClient);
		
	}
	@Test
	@Order(1)
	void loginAdmin() {
		Map<String, Object> userAdmin = new HashMap<String, Object>();
		userAdmin.put("user", "admin");
		userAdmin.put("pass", "admin");
		token = RestAssured.given().headers(userAdmin).given().get("/login").then().statusCode(200).extract().asString();
		System.out.println("token is :" + token);

	}
	List<FoodCoupon> coupons = new ArrayList<FoodCoupon>();
	@Test
	@Order(2)

	void registerRestaurants(){
		 
		Map<String, Object> headersFirstRestaurant = new HashMap<String, Object>();
		headersFirstRestaurant.put("user", "Restaurant");
		headersFirstRestaurant.put("pass", "2020");
		headersFirstRestaurant.put("token", token);
		Restaurant firstRestaurant = new Restaurant();
		firstRestaurant.setId(0);
		firstRestaurant.setUsername("");
		firstRestaurant.setPassword("");
		firstRestaurant.setRole(null);
		firstRestaurant.setName("SushiOshi");
		firstRestaurant.setCoupons(coupons);

		String myRestaurant = RestAssured.given().headers(headersFirstRestaurant).body(firstRestaurant)
				.contentType(MediaType.APPLICATION_JSON_VALUE).when().post("/register/restaurant")
				.then().statusCode(201).extract().asString();
		System.out.println(myRestaurant);
		//------------------------------------------------------------------------------
		Map<String, Object> headersSecondRestaurant = new HashMap<String, Object>();
		headersSecondRestaurant.put("user", "RestaurantDalal");
		headersSecondRestaurant.put("pass", "2020");
		headersSecondRestaurant.put("token", token);
		Restaurant secondRestaurant = new Restaurant();
		secondRestaurant.setId(0);
		secondRestaurant.setUsername("");
		secondRestaurant.setPassword("");
		secondRestaurant.setRole(null);
		secondRestaurant.setName("Dalal");
		secondRestaurant.setCoupons(coupons);

		String RestaurantDalal= RestAssured.given().headers(headersSecondRestaurant).body(firstRestaurant)
		.contentType(MediaType.APPLICATION_JSON_VALUE).when().post("/register/restaurant")
		.then().statusCode(201).extract().asString();

		System.out.println(RestaurantDalal);

	}


	private static Restaurant[]allRestaurants =null;

	@Test
	@Order(3)
	void getAllRestaurants() {

		allRestaurants = RestAssured.given().header("token", token).get("/admin/getAllRestaurants").then().statusCode(200)
				.extract().as(Restaurant[].class);
		for (int i=0; i<allRestaurants.length;i++) {
			if(allRestaurants[i].getId()>clientId) {
				restaurantId =	allRestaurants[i].getId();
				System.out.println(restaurantId);
			}

		}
		System.out.println(allRestaurants);

	}

	@Test
	@Order(4)
	void deleteRestaurant() {

		Restaurant restaurantToRemove = null;

		for (int i=0; i<allRestaurants.length;i++)
		{
			if (allRestaurants[i].getId() == restaurantId)
			{
				restaurantToRemove = allRestaurants[i];
				break;
			}

		}

		String resuts=RestAssured.given().header("token",token).body(restaurantToRemove).contentType(MediaType.APPLICATION_JSON_VALUE)
				.when().delete("/admin/deleteRestaurant").then().statusCode(200).extract().asString();
		System.out.println(resuts);

	}

	private static Client[]allClients=null;

	@Test
	@Order(5)
	void getAllClients(){
		
		allClients = RestAssured.given().header("token", token).get("/admin/getAllClients").then().statusCode(200)
		.extract().as(Client[].class);

		for (int i=0; i<allClients.length;i++) {
			if(allClients[i].getId()>clientId) {
				clientId =	allClients[i].getId();
				System.out.println(clientId);
			}
		}
	}
	
	@Test
	@Order(6)
	void deleteOneClient() {
		Client clientToRemove = null;
		for (int i=0; i<allClients.length;i++)
		{
			if (allClients[i].getId() == clientId)
			{
				clientToRemove = allClients[i];
				break;
			}
		}

		String result=RestAssured.given().header("token", token).body(clientToRemove).contentType(MediaType.APPLICATION_JSON_VALUE).when()
		.delete("/admin/deleteClient").then().statusCode(200).extract().asString();
		System.out.println(result);
	}

	@Test
	@Order(7) 
	void logoutAdmin() {
		String logOutAdmin= RestAssured.given().header("token", token).get("/logout").then().statusCode(200)
				.extract().asString();

		System.out.println(logOutAdmin);
	}



	@Test
	@Order(8) 
	void loginRestaurant(){
		///login
		Map<String, Object> userRestaurant = new HashMap<String, Object>();
		userRestaurant.put("user", "RestaurantDalal");
		userRestaurant.put("pass", "2020");
		token = RestAssured.given().headers(userRestaurant).given()
				.get("/login").then().statusCode(200).extract().asString();
		System.out.println(token);
	}

	@Test
	@Order(9)
	void addCoupons(){

		FoodCoupon couponNr1 = new FoodCoupon(0, "coupon1", "10% discount on falafel", 0);
		RestAssured.given().headers("token", token).body(couponNr1)
		.contentType(MediaType.APPLICATION_JSON_VALUE).when().post("/restaurant/addCoupon")
		.then().statusCode(201).extract().asString();
		System.out.println(couponNr1);
		

		FoodCoupon couponNr2 = new FoodCoupon(0, "coupon2", "20% discount on falafel", 0);
		RestAssured.given().headers("token", token).body(couponNr2)
		.contentType(MediaType.APPLICATION_JSON_VALUE).when().post("/restaurant/addCoupon")
		.then().statusCode(201).extract().asString();
		System.out.println(couponNr2);
	}

	private static FoodCoupon[]allCoupons=null;

	@Test
	@Order(10)
	void getAllCoupons () {
		allCoupons = RestAssured.given().header("token", token).get("/restaurant/myCoupons").then().statusCode(200)
				.extract()
				.as(FoodCoupon[].class);
		System.out.println(allCoupons[0]);
		System.out.println(allCoupons[1]);

		for (int i=0; i<allCoupons.length;i++) {
			if(allCoupons[i].getId()>couponId) {
				couponId =	allCoupons[i].getId();
				System.out.println(couponId);
			}

		}

	}
	@Test
	@Order(11)
	void DeleteOneCoupon(){
		String resultOfTheDelete= RestAssured.given().header("token",token).queryParam("id", couponId).delete("/restaurant/removeCoupon")
				.then().statusCode(200).extract().asString();
		System.out.println(resultOfTheDelete);
		System.out.println(couponId);
	}

	@Test
	@Order(12)
	void UpdateRestaurantInfo() {
		Restaurant updatedRestaurant = new Restaurant();
		updatedRestaurant.setId(couponId);
		updatedRestaurant.setUsername("");
		updatedRestaurant.setPassword("");
		updatedRestaurant.setRole(null);
		updatedRestaurant.setName("updated");
		updatedRestaurant.setCoupons(coupons);
		String result= RestAssured.given().header("token",token).body(updatedRestaurant).contentType(MediaType.APPLICATION_JSON_VALUE).when()
		.put("/restaurant/update").then().statusCode(200).extract().asString();
		System.out.println(result);
	}

	@Test
	@Order(13)
	void restaurantInfo() {
		String infoRestaurant= RestAssured.given().header("token", token).get("/restaurant/myInfo").then().statusCode(200)
				.extract().asString();
		System.out.println(infoRestaurant);
	}
	@Test
	@Order(14)
	void logoutRestaurant() {
		String logOutRestaurant= RestAssured.given().header("token", token).get("/logout").then().statusCode(200)
				.extract().asString();

		System.out.println(logOutRestaurant);
	}
	@Test
	@Order(15)
	void loginClient() {
		///login
		Map<String, Object> userClient = new HashMap<String, Object>();
		userClient.put("user", "Pati");
		userClient.put("pass", "123");
		token = RestAssured.given().headers(userClient).given()
				.get("/login").then().statusCode(200).extract().asString();
		System.out.println(token);
	}
	private static FoodCoupon currentCoupons= null;
	
	
	@Test
	@Order(16)
	void getAllCouponsByClient() {

		ArrayList<LinkedHashMap>c=RestAssured.given().header("token", token).get("/client/allCoupons").then().statusCode(200)	
				.extract().as(ArrayList.class);


		currentCoupons = new FoodCoupon(new Long((int)c.get(0).get("id")), (String)c.get(0).get("name"), (String)c.get(0).get("info"), Long.valueOf((int)c.get(0).get("ownerRestaurantId")));
		System.out.println(currentCoupons);
	}
	@Test
	@Order(17)
	void buyCouponByClient() {
		 
		String result =RestAssured.given().header("token", token).pathParam("id",currentCoupons.getId()).when().post("/client/buy/{id}")
		.then().statusCode(200).extract().asString();
		System.out.println(result);
	}
	@Test
	@Order(18)
	void useCouponByClient() {
		String result= RestAssured.given().header("token", token).pathParam("id",currentCoupons.getId()).when()
		.delete("/client/use/{id}").then().statusCode(200)
		.extract().asString();
		System.out.println(result);
	}
	@Test
	@Order(19)
	void clientUpdate() {

		Client updatedClient = new Client();
		updatedClient.setId(clientId);
		updatedClient.setUsername("");
		updatedClient.setPassword("");
		updatedClient.setRole(null);
		updatedClient.setFirstName("Yulia");
		updatedClient.setLastName("Victory");
		updatedClient.setAge(10);
		String result= RestAssured.given().header("token",token).body(updatedClient).contentType(MediaType.APPLICATION_JSON_VALUE).when()
		.put("/client/update").then().statusCode(200).extract().asString();
		System.out.println(result);
	}
	
	@Test
	@Order(20)
	void clientInfo() {
 
		String infoClient= RestAssured.given().header("token", token).get("/client/myInfo").then().statusCode(200)
				.extract().asString();
		System.out.println(infoClient);
	}
	@Test
	@Order(21)
	void clientLogout() {
		String logOutClient= RestAssured.given().header("token", token).get("/logout").then().statusCode(200)
				.extract().asString();

		System.out.println(logOutClient);
		counter=1;

	}

}





