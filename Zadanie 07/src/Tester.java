// Autor: ignis

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.LinkedList;

public class Tester {
	static Random random;
	
	public static class HPS implements HidingPlaceSupplier {
		private AtomicBoolean isOpening = new AtomicBoolean(false); // if a place is being opened atm
		private AtomicInteger detectedThreads = new AtomicInteger(0);
		private AtomicInteger detectedThreads2 = new AtomicInteger(0);
		private AtomicInteger maxDetThreads = new AtomicInteger(0);
		private int threads;
		private List<Double> values;
		private Integer i = 0;

		public HPS(List<Double> values) {
			this.threads = (int) ((double) (values.remove(0)));
			this.values = values;
		}

		class HP implements HidingPlace {
			private double value;
			private volatile boolean isOpened = false;

			public HP(double value) {
				this.value = value;
			}

			@Override
			public boolean isPresent() {
				System.out.println(Thread.currentThread().getName() + " checks if value is present in HP");
				// check thread limit
				if (detectedThreads.incrementAndGet() > threads)
					throw new RuntimeException("Detected more threads than maximum allowed");
				// save max thread count
				if (detectedThreads.get() > maxDetThreads.get())
					maxDetThreads.set(detectedThreads.get());
				try {
					Thread.sleep(50 + random.nextInt(200));
				} catch (Exception e) {
					System.err.println(e);
				}
				detectedThreads.decrementAndGet();
				System.out.println(Thread.currentThread().getName() + "'s HP " + (this.value != 0 ? "contains the value" : "is empty"));
				return this.value != 0;
			}

			@Override
			public double openAndGetValue() {
				System.out.println(Thread.currentThread().getName() + " wants to open the HP");
				if (!isOpening.compareAndSet(false, true))
					throw new RuntimeException("Attempted to open multiple boxes at the same time");
				if (this.isOpened)
					throw new RuntimeException("Attempted to open the same box more than once");
				try {
					Thread.sleep(50 + random.nextInt(200));
				} catch (Exception e) {
					System.err.println(e);
				}
				isOpening.set(false);
				this.isOpened = true;
				System.out.println(Thread.currentThread().getName() + " got value " + this.value + " out of the HP");
				return this.value;
			}

		}

		@Override
		public HidingPlaceSupplier.HidingPlace get() {
			System.out.println(Thread.currentThread().getName() + " requested HidingPlace");
			// check thread limit
			if (detectedThreads2.incrementAndGet() > threads)
				throw new RuntimeException("Detected more threads than maximum allowed");
			// save max thread count
			if (detectedThreads2.get() > maxDetThreads.get())
				maxDetThreads.set(detectedThreads2.get());
			try {
				Thread.sleep(50 + random.nextInt(200));
			} catch (Exception e) {
				System.err.println(e);
			}
			detectedThreads2.decrementAndGet();
			synchronized (i) {
				if (i < this.values.size()) {
					System.out.println(Thread.currentThread().getName() + " received next HidingPlace");
					return new HP(this.values.get(i++));
				} else {
					System.out.println(Thread.currentThread().getName() + " received null (HPS is empty)");
					return null;
				}

			}
		}

		@Override
		public int threads() {
			return threads;
		}

		public boolean maxThreadsReached() {
			return maxDetThreads.get() == this.threads;
		}

	}

	public static class HPSS implements HidingPlaceSupplierSupplier {
		List<List<Double>> hpsValues;
		double expValue = 0;
		volatile int counter = 0;
		private HPS lastHps = null;

		HPSS(List<List<Double>> hpsValues) {
			this.hpsValues = hpsValues;
		}

		public boolean isEmpty() {
			return this.hpsValues.size() == 0;
		}

		@Override
		public HidingPlaceSupplier get(double totalValueOfPreviousObject) {
			if (Math.round(totalValueOfPreviousObject) == Math.round(expValue)) {
				// check if lask hps reached max threads
				if (lastHps != null) {
					if (!lastHps.maxThreadsReached())
						throw new RuntimeException("HPS registered less theards than its expected value");
				}

				// create next hps
				System.out.println("\nRequested new HPS (" + counter++ + ") by passing value " + expValue);
				if (hpsValues.size() == 0) {
					System.out.println("HPSS is out of suppliers");
					return null;
				}
				var hpValues = hpsValues.remove(0);
				expValue = hpValues.stream().mapToDouble(Double::doubleValue).sum() - hpValues.get(0); // subtract thread count
				lastHps = new HPS(hpValues);
				return lastHps;
			} else
				throw new RuntimeException("Invalid totalValueOfPreviousObject:\nExpected: " + expValue + ", Received: " + totalValueOfPreviousObject);
		}

	}

	static List<Double> genHpsValues(int threads, int boxCount) {
		List<Double> innerValues = new ArrayList<Double>(boxCount + 1);
		// first value = number of required threads
		innerValues.add((double) threads);
		// randomly generate values for the boxes
		for (int j = 0; j < boxCount; j++) {
			// 1/3 chance for empty box
			if (random.nextInt(10) <= 3)
				innerValues.add(0.);
			else
				innerValues.add((random.nextDouble() * 30) - 10); // random double from -10 to 20
		}
		return innerValues;
	}

	public static void main(String[] args) {
		// create rng
		random = new Random(21377312);

		List<List<Double>> hpsValues = new LinkedList<List<Double>>();

		// 3 threads 10 boxes, simple fist test
		hpsValues.add(genHpsValues(3, 10));

		// 10 threads, 3 boxes, checks if all threads attempt to get a box
		hpsValues.add(genHpsValues(10, 3));

		// 8 random HidingPlaceSuppliers
		for (int i = 0; i < 8; i++) {
			hpsValues.add(genHpsValues(5 + random.nextInt(6), 8 + random.nextInt(13)));
		}

		// run search
		var hpss = new HPSS(hpsValues);
		var searcher = new ParallelSearcher();
		searcher.set(hpss);

		// check if hpss got emptied
		if (hpss.isEmpty())
			System.out.println("Succesfully emptied HPSS");
		else
			System.out.println("Failed to empty HPSS");
	}
}
