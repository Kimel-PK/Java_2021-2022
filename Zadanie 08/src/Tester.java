// Autor: ignis

import java.lang.Thread.State;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class Tester {
	static Random random = new Random(21372137);

	static class Client extends Thread {
		public Shop shop;
		public String productName;
		public int quantity;
		public Boolean result = null;

		public Client(Shop shop, String productName, int quantity) {
			this.shop = shop;
			this.productName = productName;
			this.quantity = quantity;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(random.nextInt(50));
			} catch (InterruptedException e) {
			}
			result = shop.purchase(productName, quantity);
			System.out.println(Thread.currentThread().getName() + (result ? " purchased " : " failed to purchase ") + quantity + " " + productName);
		}
	}

	static class Delivery extends Thread {
		public Shop shop;
		public String productName;
		public int quantity;

		public Delivery(Shop shop, String productName, int quantity) {
			this.shop = shop;
			this.productName = productName;
			this.quantity = quantity;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(random.nextInt(50));
			} catch (InterruptedException e) {
			}
			var map = new HashMap<String, Integer>();
			map.put(productName, quantity);
			shop.delivery(map);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		var threadList = new LinkedList<Client>();

		var shop = new Shop();
		var shop2 = new Shop();

		// client waiting for product that wasn't delivered
		var newTh = new Client(shop, "product3", 20);
		threadList.add(newTh);
		newTh.start();

		Thread.sleep(300);

		// create store
		Map<String, Integer> goods = new HashMap<String, Integer>();
		goods.put("product1", 8);
		goods.put("product2", 12);
		goods.put("product3", 21);
		goods.put("product4", 4);
		shop.delivery(goods);
		shop2.delivery(goods);

		// create and start threads
		threadList.add(new Client(shop, "product1", 5));
		threadList.add(new Client(shop, "product1", 5));
		threadList.add(new Client(shop, "product1", 5));
		threadList.add(new Client(shop, "product2", 4));
		threadList.add(new Client(shop, "product2", 4));
		threadList.add(new Client(shop, "product2", 4));

		threadList.add(new Client(shop2, "product1", 9));
		for (var th : threadList)
			if (th.getState().equals(State.NEW))
				th.start();

		// product 1 delivery after one second
		Thread.sleep(500);
		// check for timed waits
		for (var th : threadList)
			if (th.getState().equals(State.TIMED_WAITING))
				throw new RuntimeException("Found thread on timed wait");
		Thread.sleep(500);
		System.out.println("=== 1000 sleep ===");
		goods = new HashMap<String, Integer>();
		goods.put("product1", 4);
		shop.delivery(goods);

		// wait another second
		Thread.sleep(1000);
		System.out.println("=== 1000 sleep ===");
		if (!threadList.get(threadList.size() - 1).getState().equals(State.WAITING)) {
			throw new RuntimeException("Adding stock to shop1 released thread waiting for stock in shop2");
		}
		// shop2 delivery
		shop2.delivery(goods);

		// wait for threads
		Thread.sleep(1000);
		for (var th : threadList)
			if (!th.getState().equals(State.TERMINATED))
				throw new RuntimeException("Some threads are still running");

		// check threads that failed to buy
		Client failed = null;
		for (var th : threadList)
			if (!th.result) {
				if (failed != null)
					throw new RuntimeException("More clients failed purchase than expected");
				failed = th;
			}
		if (!failed.productName.equals("product1"))
			throw new RuntimeException("Wrong client failed to purchase");

		// check if stock is as expected
		if (!shop.stock().toString().equals("{product2=0, product1=2, product4=4, product3=1}"))
			throw new RuntimeException("Invalid result stock");

		// 25 deliveries at the same time
		System.out.println("=== Adding 1 of product 4 in 25 threads ===");
		Delivery[] delThs = new Delivery[25];
		for (int i = 0; i < 25; i++) {
			Delivery del = new Delivery(shop, "product4", 1);
			delThs[i] = del;
			del.start();
		}
		for (var del : delThs)
			del.join();

		if (!shop.stock().get("product4").equals(29)) {
			throw new RuntimeException("Invalid stock after parallel deliveries: expected: 29, got: " + shop.stock().get("product4"));
		}

		System.out.println("If there are no errors above, then all tests passed");
	}
}
