/**
 * @author Mehrdad Sabetzadeh, Ismet Bilgic, University of Ottawa
 *
 */
public class Simulator {

	/**
	 * Length of car plate numbers
	 */
	public static final int PLATE_NUM_LENGTH = 3;

	/**
	 * Number of seconds in one hour
	 */
	public static final int NUM_SECONDS_IN_1H = 3600;

	/**
	 * Maximum duration a car can be parked in the lot
	 */
	public static final int MAX_PARKING_DURATION = 8 * NUM_SECONDS_IN_1H;

	/**
	 * Total duration of the simulation in (simulated) seconds
	 */
	public static final int SIMULATION_DURATION = 24 * NUM_SECONDS_IN_1H;

	/**
	 * The probability distribution for a car leaving the lot based on the duration
	 * that the car has been parked in the lot
	 */
	public static final TriangularDistribution departurePDF = new TriangularDistribution(0, MAX_PARKING_DURATION / 2,
			MAX_PARKING_DURATION);

	/**
	 * The probability that a car would arrive at any given (simulated) second
	 */
	private Rational probabilityOfArrivalPerSec;

	/**
	 * The simulation clock. Initially the clock should be set to zero; the clock
	 * should then be incremented by one unit after each (simulated) second
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
	 * @param lot   is the parking lot to be simulated
	 * @param steps is the total number of steps for simulation
	 */
	public Simulator(ParkingLot lot, int perHourArrivalRate, int steps) {

		if (lot == null) {
			throw new IllegalArgumentException("Parking lot cannot be null."); // if the lot is null, throw an exception
		}

		if (perHourArrivalRate < 0) {
			throw new IllegalArgumentException("Arrival rate cannot be negative."); // if the arrival rate is negative, throw an exception
		}

		if (steps <= 0) {
			throw new IllegalArgumentException("Simulation steps must be positive."); // if the number of steps is not positive, throw an exception
		}

		this.lot = lot;
		this.steps = steps;
		this.probabilityOfArrivalPerSec = new Rational(perHourArrivalRate, NUM_SECONDS_IN_1H);
		this.clock = 0;
		this.incomingQueue = new LinkedQueue<Spot>();
		this.outgoingQueue = new LinkedQueue<Spot>();
		
	}

	private void processArrival() {
		boolean shouldAddNewCar = RandomGenerator.eventOccurred(probabilityOfArrivalPerSec); // generate a random number and check if it is less than the probability of arrival

		if (shouldAddNewCar){ // if the random number is less than the probability of arrival, add a new car to the incoming queue
			Car car = new Car(RandomGenerator.generateRandomString(PLATE_NUM_LENGTH)); // generate a random string for the car plate number	
			incomingQueue.enqueue(new Spot(car, clock)); // add the car to the incoming queue
		}
	}

	private void processDeparture() {
		for (int i = 0; i < lot.getOccupancy(); i++){ // iterate through the parking lot
			Spot spot = lot.getSpotAt(i); // get the spot at the current index

			if (spot == null){
				continue; // if the spot is null, continue to the next iteration
			}

			if (spot != null ){
				int duration = clock - spot.getTimestamp(); // calculate the duration of the car's stay in the parking lot
				boolean willLeave = false; // initialize a boolean variable to check if the car will leave the parking lot

				if (duration == MAX_PARKING_DURATION){ // if the duration is equal to the maximum parking duration, the car will leave the parking lot
					willLeave = true; // set the boolean variable to true
				} 
				else {
					willLeave = RandomGenerator.eventOccurred(departurePDF.pdf(duration)); // generate a random number and check if it is less than the probability of departure
				}
				if (willLeave) {

					Spot toExit = lot.remove(i); // remove the car from the parking lot

					toExit.setTimestamp(clock); // set the timestamp of the car to the current time

					outgoingQueue.enqueue(spot); // add the car to the outgoing queue
				}
			}
		}
		
	}

	/**
	 * Simulate the parking lot for the number of steps specified by the steps
	 * instance variable
	 * NOTE: Make sure your implementation of simulate() uses peek() from the Queue interface.
	 */
	public void simulate() {
		if (incomingQueue == null) { // if the incoming queue is null, throw an exception
			throw new IllegalStateException("Incoming queue is null.");
		}
		if (outgoingQueue == null) { // if the outgoing queue is null, throw an exception
			throw new IllegalStateException("Outgoing queue is null.");
		}

		while (clock < steps) {
			processArrival(); // process the arrival of a car
			processDeparture(); // process the departure of a car
			if (!incomingQueue.isEmpty()){ // if the incoming queue is not empty
				Spot incomingToProcess = incomingQueue.peek(); // get the first car in the incoming queue
				if (incomingToProcess != null) { // if the car is not null
					boolean isProcessed = lot.attemptParking(incomingToProcess.getCar(), clock); // attempt to park the car in the parking lot

					if (isProcessed) {
						incomingQueue.dequeue(); // remove the car from the incoming queue
					}
				}

			}
			if (!outgoingQueue.isEmpty()) {
				outgoingQueue.dequeue(); // remove the car from the outgoing queue
			} 

			clock++; // increment the clock by 1
		}
	}
	
	public int getIncomingQueueSize() {
		if (incomingQueue == null) {
			throw new IllegalStateException("Incoming queue is null."); // if the incoming queue is null, throw an exception
		}
		return incomingQueue.size(); // return the size of the incoming queue
	}
}
