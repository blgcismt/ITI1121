/**
 * @author Mehrdad Sabetzadeh, Ismet Bilgic, University of Ottawa
 *
 */
public class Simulator {

	/**
	 * Maximum duration a car can be parked in the lot
	 */
	public static final int MAX_PARKING_DURATION = 8 * 3600;

	/**
	 * Total duration of the simulation in (simulated) seconds
	 */
	public static final int SIMULATION_DURATION = 24 * 3600;

	/**
	 * The probability distribution for a car leaving the lot based on the duration
	 * that the car has been parked in the lot
	 */
	public static final TriangularDistribution departurePDF = new TriangularDistribution(0, MAX_PARKING_DURATION / 2,
			MAX_PARKING_DURATION);

	/**
	 * The probability that a car would arrive at any given (simulated) second
	 * This probability is calculated in the constructor based on the perHourArrivalRate
	 * passed to the constructor.
	 */
	private Rational probabilityOfArrivalPerSec;

	/**
	 * The simulation clock. Initially the clock should be set to zero; the clock
	 * should then be incremented by one unit after each (simulated) second.
	 */
	private int clock;

	/**
	 * Total number of steps (simulated seconds) that the simulation should run for.
	 * This value is fixed at the start of the simulation. The simulation loop
	 * should be executed for as long as clock < steps. When clock == steps, the
	 * simulation is finished.
	 */
	private int steps;

	/**
	 * Instance of the parking lot being simulated.
	 */
	private ParkingLot lot;

	/**
	 * Queue for the cars wanting to enter the parking lot
	 */
	private Queue<Spot> incomingQueue;

	/**
	 * Queue for the cars wanting to leave the parking lot
	 */
	private Queue<Spot> outgoingQueue;

	/**
	 * @param lot                 is the parking lot to be simulated
	 * @param perHourArrivalRate  is the HOURLY rate at which cars show up in front of the lot
	 * @param steps               is the total number of steps for simulation
	 */
	public Simulator(ParkingLot lot, int perHourArrivalRate, int steps) {
		this.lot = lot;
		this.steps = steps;
		this.probabilityOfArrivalPerSec = new Rational(perHourArrivalRate, 3600); // 1 hour = 3600 seconds so we divide by 3600 to get the probability of arrival per second
		this.incomingQueue = new LinkedQueue<Spot>(); // queue for incoming cars
		this.outgoingQueue = new LinkedQueue<Spot>(); // queue for outgoing cars
	}

	/**
	 * Simulate the parking lot for the number of steps specified by the steps
	 * instance variable.
	 * In this method, you will implement the algorithm shown in Figure 3 of the A2 description.
	 */
	public void simulate() {
		clock = 0; // set the clock to 0
		while(clock < steps){ // while the clock is less than the number of steps
			if(RandomGenerator.eventOccurred(probabilityOfArrivalPerSec)){ // if a car arrives
				Spot spot = new Spot(RandomGenerator.generateRandomCar(), clock); // create a new spot with a random car and the current time
				incomingQueue.enqueue(spot); // add the spot to the incoming queue
			}

			for(int row= 0; row<lot.getNumRows(); row++){ // for each row
				for(int column= 0; column<lot.getNumSpotsPerRow(); column++){ // for each spot in the row
					if(lot.getSpotAt(row, column) != null){ // if the spot is not empty
						Car car = lot.getSpotAt(row, column).getCar(); // get the car in the spot
						Spot spot = lot.getSpotAt(row, column); // get the spot
						int duration = clock - spot.getTimestamp(); // calculate the duration the car has been parked
						if(duration == 8*3600){ // if the car has been parked for 8 hours
							lot.remove(row, column); // remove the car from the spot
							outgoingQueue.enqueue(new Spot(car, clock)); // add the car to the outgoing queue
						}
						else if(RandomGenerator.eventOccurred(departurePDF.pdf(duration))){ // if the car leaves
							lot.remove(row, column); // remove the car from the spot
							outgoingQueue.enqueue(new Spot(car, clock)); // add the car to the outgoing queue
						}
					}
				}
			}

			if(!incomingQueue.isEmpty()){ // if the incoming queue is not empty
				Spot spot = incomingQueue.dequeue(); // get the first spot in the queue
				if(lot.attemptParking(spot.getCar(), spot.getTimestamp())){ // if the car can be parked
					System.out.println(spot.getCar() + " ENTERED at timestep " + clock + "; occupancy is at " + lot.getTotalOccupancy());
				}
				else{
					incomingQueue.enqueue(spot); // if the car cannot be parked, add it back to the queue
				}
			}

			if(!outgoingQueue.isEmpty()){ // if the outgoing queue is not empty
				Spot spot = outgoingQueue.dequeue(); // get the first spot in the queue
				System.out.println(spot.getCar() + " EXITED at timestep " + clock + "; occupancy is at " + lot.getTotalOccupancy());
			}
			clock++;
		}
	}

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the parking-lot design. Next, it simulates the parking lot
	 * for a number of steps (this number is specified by the steps parameter). At
	 * the end, the method prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();
		
		if (args.length < 2) {
			System.out.println("Usage: java Simulator <lot-design filename> <hourly rate of arrival>");
			System.out.println("Example: java Simulator parking.inf 11");
			return;
		}

		if (!args[1].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		ParkingLot lot = new ParkingLot(args[0]);

		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		Simulator sim = new Simulator(lot, Integer.parseInt(args[1]), SIMULATION_DURATION);

		long start, end;

		System.out.println("=== SIMULATION START ===");
		start = System.currentTimeMillis();
		sim.simulate();
		end = System.currentTimeMillis();
		System.out.println("=== SIMULATION END ===");

		System.out.println();

		System.out.println("Simulation took " + (end - start) + "ms.");

		System.out.println();

		int count = 0;

		// The Queue interface does not provide a method to get the size of the queue.
		// We thus have to dequeue all the elements to count how many elements the queue has!
		
		while (!sim.incomingQueue.isEmpty()) {
			sim.incomingQueue.dequeue();
			count++;
		}

		System.out.println("Length of car queue at the front at the end of simulation: " + count);
	}
}
