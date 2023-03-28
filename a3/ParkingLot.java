/**
 * @author Mehrdad Sabetzadeh, Ismet Bilgic, University of Ottawa
 */
public class ParkingLot {

	// IMPORTANT: You are *discouraged* from defining any new instance variables in
	// ParkingLot. You are expected to provide a list-based implementation of
	// ParkingLot. Defining new instance variables can take you away from this
	// implementation goal and thus result in the loss of marks.
	/**
	 * List for storing occupancy information in the lot
	 */
	private List<Spot> occupancy;

	/**
	 * The maximum number of cars that the lot can accommodate
	 */
	private int capacity;

	/**
	 * Constructs a parking lot with a given (maximum) capacity
	 * 
	 * @param capacity is the (maximum) capacity of the lot
	 */
	public ParkingLot(int capacity) { 
		if (capacity < 0) { // if the capacity is negative 
			throw new IllegalArgumentException("Capacity cannot be negative!"); // throw an exception
		}
		this.capacity = capacity;
		this.occupancy = new SinglyLinkedList<Spot>();
	}

	/**
	 * Parks a car (c) in the parking lot.
	 * 
	 * @param c         is the car to be parked
	 * @param timestamp is the (simulated) time when the car gets parked in the lot
	 */
	public void park(Car c, int timestamp) {
		if (this.occupancy.size() < this.capacity) { // if the lot is not full
			Spot s = new Spot(c, timestamp); // create a new spot for the car
			this.occupancy.add(s); // add the spot to the occupancy list
		} 
		else {
			throw new IllegalStateException("Parking lot is full!"); // if the lot is full, throw an exception
		}
	
	}


	/**
	 * Removes the car (spot) parked at list index i in the parking lot
	 * 
	 * @param i is the index of the car to be removed
	 * @return the car (spot) that has been removed
	 */
	public Spot remove(int i) {
		if (i < 0 || i >= this.occupancy.size()) { // if the index is out of bounds
			throw new IllegalArgumentException("Index is out of bounds!"); // throw an exception
		}
		return this.occupancy.remove(i); // remove the spot at index i
	}

	public boolean attemptParking(Car c, int timestamp) {
		if (c == null) { // if the car is null
			throw new IllegalArgumentException("Car cannot be null!"); // throw an exception
		}

		if (timestamp < 0) { // if the timestamp is negative
			throw new IllegalArgumentException("Timestamp cannot be negative!"); // throw an exception
		}
		
		if (this.occupancy.size() < this.capacity) { // if the lot is not full
			this.park(c, timestamp); // park the car
			return true; // return true
		} 
		else {
			return false; // if the lot is full, return false
		}

	}

	/**
	 * @return the capacity of the parking lot
	 */
	public int getCapacity() {
		if (this.capacity < 0) { // if the capacity is negative
			throw new IllegalStateException("Capacity cannot be negative!"); // throw an exception
		}
		return capacity; // return the capacity of the lot
	}

	/**
	 * Returns the spot instance at a given position (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the spot instance at a given position (i, j)
	 */
	public Spot getSpotAt(int i) {
		if (i < 0 || i >= this.occupancy.size()) { // if the index is out of bounds
			throw new IllegalArgumentException("Index is out of bounds!"); // throw an exception
		}
		if (this.occupancy.isEmpty()) { // if the lot is empty
			throw new IllegalStateException("Parking lot is empty!"); // throw an exception
		}

		return occupancy.get(i); // return the spot at index i
	}

	/**
	 * @return the total number of cars parked in the lot
	 */
	public int getOccupancy() { 
		if (this.occupancy == null) { // if the occupancy list is null
			throw new IllegalStateException("Occupancy list is null!"); // throw an exception
		}
		return occupancy.size(); // return the size of the occupancy list
	}

	/**
	 * @return String representation of the parking lot
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Total capacity: " + this.capacity + System.lineSeparator());
		buffer.append("Total occupancy: " + this.occupancy.size() + System.lineSeparator());
		buffer.append("Cars parked in the lot: " + this.occupancy + System.lineSeparator());

		return buffer.toString();
	}
}
