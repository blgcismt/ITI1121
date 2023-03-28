/**
 * @author Mehrdad Sabetzadeh, Ismet Bilgic, University of Ottawa
 *
 */
public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

	public static int getOptimalNumberOfSpots(int hourlyRate) {
		int lotSize = 1; //initial lot size
		while (true) { //loop until we find the optimal lot size
			double average = 0.0d; //average queue size
			long end,start; // start and end time of each simulation run
			System.out.println("==== Setting lot capacity to: " + lotSize + " ===="); //print the current lot size 
			for (int i = 0; i < NUM_RUNS; i++) { //run the simulation NUM_RUNS times
				ParkingLot lot = new ParkingLot(lotSize); //create a new parking lot with the current lot size
				Simulator sim = new Simulator(lot, hourlyRate, Simulator.SIMULATION_DURATION); //create a new simulator with the current parking lot and hourly rate
				start = System.currentTimeMillis(); //start the simulation
				sim.simulate(); //simulate
				end = System.currentTimeMillis(); //end the simulation
				average += sim.getIncomingQueueSize();//add the queue size to the average
				System.out.println("Simulation run " + (i + 1) + " (" + (end - start) + "ms); Queue length at the end of simulation run: " + sim.getIncomingQueueSize()); //print the queue size
			}
			average /= NUM_RUNS; //calculate the average queue size
			if (average <= THRESHOLD) { 
				break; //if the average queue size is less than the threshold, we have found the optimal lot size
			} else {
				lotSize++; //if the average queue size is greater than the threshold, increase the lot size and try again
			}
		}
		return lotSize; //return the optimal lot size
	}

	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}
