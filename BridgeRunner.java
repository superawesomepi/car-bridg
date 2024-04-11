import java.util.*;
/**
 * Runs all threads
 */

public class BridgeRunner {

	public static void main(String[] args) {
		// TODO - check command line inputs
		if(args.length < 2) { // are there enough arguments
			System.out.println("Usage: java BridgeRunner <bridge limit> <num cars>");
			return;
		}
		int limit = Integer.parseInt(args[0]);
		int numCars = Integer.parseInt(args[1]);
		if(limit <= 0 || numCars <= 0) {
			System.out.println("Error: bridge limit and/or num cars must be positive.");
			return;
		}
		System.out.println("Running bridge with limit " + limit + " on " + numCars + " cars.");
		// TODO - instantiate the bridge
		OneLaneBridge oneLaneBridge = new OneLaneBridge(limit);
		// TODO - allocate space for threads
		Thread[] cars = new Thread[numCars];
		// TODO - start then join the threads
		for (int i = 0; i < numCars; i++) {
      		cars[i] = new Thread(new Car(i, oneLaneBridge));
      		cars[i].run();
    	}
		for (int i = 0; i < numCars; i++) {
      		try {
        		cars[i].join();
      		} catch (InterruptedException e) {
      		}
    	}
		System.out.println("All cars have crossed!!");
	}

}