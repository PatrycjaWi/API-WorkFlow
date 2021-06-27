package com.johnBryce.demo.layer2.userManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.johnBryce.demo.beans.Client;
import com.johnBryce.demo.beans.FoodCoupon;
import com.johnBryce.demo.layer1.db.Database;
import com.johnBryce.demo.layer2.DbObject;
import com.johnBryce.demo.layer2.auth.ClientAuth;

public class ClientManagement {

	ClientAuth cAuth = new ClientAuth();
	Database db = DbObject.getDB();

	public String buyCoupon(long id, long couponId) throws Exception {
		List<FoodCoupon> clientCurrentCoupons = db.getClientCoupons().get(id);

		if (clientCurrentCoupons == null) {
			clientCurrentCoupons = new ArrayList<FoodCoupon>();
		}

		FoodCoupon coupon = db.getCoupons().get(couponId);

		if (coupon == null) {
			throw new Exception("coupon with id: " + couponId + " does not exist");
		}

		if (!clientCurrentCoupons.contains(coupon)) {
			clientCurrentCoupons.add(coupon);
		} else {
			throw new Exception("client already own this coupon");
		}

		db.getClientCoupons().put(id, clientCurrentCoupons);
		return "coupon bought";
	}

	public String useCoupon(long id, long couponId) throws Exception {
		List<FoodCoupon> clientCurrentCoupons = db.getClientCoupons().get(id);

		if (clientCurrentCoupons != null) {
			FoodCoupon coupon = null;

			for (FoodCoupon singleCoupon : clientCurrentCoupons) {
				if (singleCoupon.getId() == couponId) {
					coupon = singleCoupon;
				}
			}

			if (coupon != null) {
				clientCurrentCoupons.remove(coupon);
			} else {
				throw new Exception("client do not own this coupon");
			}
		} else {
			throw new Exception("client own 0 coupons");
		}

		db.getClientCoupons().put(id, clientCurrentCoupons);
		return "have fun using your coupon";
	}

	public Client getClientById(long id) throws Exception {
		Client client = db.getClientsData().get(id);
		if (client != null) {
			return client;
		} else {
			throw new Exception("client does not exists");
		}
	}

	public String addClient(Client client) throws Exception {
		if (db.getClientsData().get(client.getId()) == null) {
			db.getClientsData().put(client.getId(), client);
			return "client added";
		} else {
			throw new Exception("client already exist");
		}
	}

	public String updateClient(Client client) throws Exception {
		Client oldClient = db.getClientsData().get(client.getId());

		if (oldClient != null) {
			oldClient.setAge(client.getAge());
			oldClient.setFirstName(client.getFirstName());
			oldClient.setLastName(client.getLastName());
			db.getClientsData().put(oldClient.getId(), oldClient);

			return "client updated";
		} else {
			throw new Exception("server error");
		}
	}

	public String removeClient(Client client) throws Exception {
		if (db.getClientsData().remove(client.getId(), client)) {

			db.getClientCoupons().remove(client.getId());

			db.getUsersLoginData().remove(client.getUsername());

			return "client removed";
		} else {
			throw new Exception("client does not exists");
		}
	}

	public List<Client> getAllClients() {
		List<Client> clients = new ArrayList<Client>();
		Map<Long, Client> clientsData = db.getClientsData();

		for (Map.Entry<Long, Client> entry : clientsData.entrySet()) {
			clients.add(entry.getValue());
		}

		return clients;
	}

	public List<FoodCoupon> getMyCoupons(long id) {
		List<FoodCoupon> myCoupons = db.getClientCoupons().get(id);

		if (myCoupons == null) {
			myCoupons = new ArrayList<FoodCoupon>();
		}

		return myCoupons;
	}

	public List<FoodCoupon> getAllCoupons() {
		List<FoodCoupon> coupons = new ArrayList<FoodCoupon>();
		Map<Long, FoodCoupon> coups = db.getCoupons();

		for (Map.Entry<Long, FoodCoupon> entry : coups.entrySet()) {
			coupons.add(entry.getValue());
		}

		return coupons;
	}

	public Client validateToken(String token) throws Exception {
		return cAuth.validateToken(token);
	}

}
