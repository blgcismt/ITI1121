import java.io.File;
import java.util.Scanner;

/**
 * @author Mehrdad Sabetzadeh, Ismet Bilgic, University of Ottawa
 */
public class ParkingLot {
	/**
	 * The delimiter that separates values
	 */
	private static final String SEPARATOR = ",";

	/**
	 * Instance variable for storing the number of rows in a parking lot
	 */
	private int numRows;

	/**
	 * Instance variable for storing the number of spaces per row in a parking lot
	 */
	private int numSpotsPerRow;

	/**
	 * Instance variable (two-dimensional array) for storing the lot design
	 */
	private CarType[][] lotDesign;

	/**
	 * Instance variable (two-dimensional array) for storing occupancy information
	 * for the spots in the lot
	 */
	private Spot[][] occupancy;

	/**
	 * Constructs a parking lot by loading a file
	 * 
	 * @param strFilename is the name of the file
	 */
	public ParkingLot(String strFilename) throws Exception {
		calculateLotDimensions(strFilename); // calculates the number of rows and spots per row
		lotDesign = new CarType[numRows][numSpotsPerRow]; // initializes the lot design
		occupancy = new Spot[numRows][numSpotsPerRow]; // initializes the occupancy
		populateDesignFromFile(strFilename); // populates the lot design
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumSpotsPerRow() {
		return numSpotsPerRow;
	}

	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i         is the parking row index
	 * @param j         is the index of the spot within row i
	 * @param c         is the car to be parked
	 * @param timestamp is the (simulated) time when the car gets parked in the lot
	 */
	public void park(int row, int column, Car c, int timestamp) {
		if ((row < 0) || (row >= numRows) || (column < 0) || (column >= numSpotsPerRow)) { // checks if the given location is out of range
			return;
		}
		if (lotDesign[row][column] == CarType.NA) { // checks if the given location is not a parking spot and returns if it is not
			return;
		}
		if (occupancy[row][column] != null) { // checks if the given location is already occupied and returns if it is
			return;
		}
		occupancy[row][column] = new Spot(c, timestamp); // parks the car at the given location
	}

	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the spot removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Spot remove(int row, int column) {
		if ((row < 0) || (row >= numRows) || (column < 0 )|| (column >= numSpotsPerRow)) { // checks if the given location is out of range
			return null;
		}
		if (lotDesign[row][column] == CarType.NA) { // checks if the given location is not a parking spot and returns null if it is not
			return null;
		}
		if (occupancy[row][column] == null) { // checks if the given location is already empty and returns null if it is
			return null;
		}
		Spot removed = occupancy[row][column]; // stores the spot at the given location in a temporary variable
		occupancy[row][column] = null; // removes the spot at the given location
		return removed; // returns the spot that was removed
	}

	/**
	 * Returns the spot instance at a given position (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the spot instance at position (i, j)
	 */
	public Spot getSpotAt(int row, int column) {
		if ((row < 0) || (row >= numRows) || (column < 0) || (column >= numSpotsPerRow)) { // checks if the given location is out of range
			return null;
		}
		return occupancy[row][column]; // returns the spot at the given location
	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 *
	 * NOTE: This method is complete; you do not need to change it.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */
	public boolean canParkAt(int row, int column, Car c) {

		if ((row < 0) || (row >= numRows) || (column < 0) || (column >= numSpotsPerRow)) {// checks if the given location is out of range
			return false;
		}
		if (lotDesign[row][column] == CarType.NA) {// checks if the given location is not a parking spot and returns false if it is not
			return false;
		}
		if (occupancy[row][column] != null) {// checks if the given location is already occupied and returns false if it is
			return false;
		}
		if (c.getType() == CarType.ELECTRIC) {// checks if the given car is an electric car and returns true if it is parkable at the given spot
			return true;
		}
		if (c.getType() == CarType.SMALL) {// checks if the car is a small car and returns true if it is parkale at the given spot
			if ((lotDesign[row][column] == CarType.SMALL) || (lotDesign[row][column] == CarType.REGULAR) || (lotDesign[row][column] == CarType.LARGE)) {
				return true;
			}
		}
		if (c.getType() == CarType.REGULAR) {// checks if the car is a regular car and returns true if it is parkable at the given spot
			if ((lotDesign[row][column] == CarType.REGULAR) || (lotDesign[row][column] == CarType.LARGE)) {
				return true;
			}
		}
		if (c.getType() == CarType.LARGE) {// checks if the car is a large car and returns true if it is parkable at the given spot
			if ((lotDesign[row][column] == CarType.LARGE)) {
				return true;
			}
		}
		return false; // returns false if the car is not parkable at the given spot
	
	}

	/**
	 * Attempts to park a car in the lot. Parking is successful if a suitable parking spot
	 * is available in the lot. If some suitable spot is found (anywhere in the lot), the car
	 * is parked at that spot with the indicated timestamp and the method returns "true".
	 * If no suitable spot is found, no parking action is taken and the method simply returns
	 * "false"
	 * 
	 * @param c is the car to be parked
	 * @param timestamp is the simulation time at which parking is attempted for car c 
	 * @return true if c is successfully parked somwhere in the lot, and false otherwise
	 */
	public boolean attemptParking(Car c, int timestamp) {
		for (int row = 0; row < numRows; row++) {// iterates through the rows of the parking lot
			for (int column = 0; column < numSpotsPerRow; column++) {// iterates through the spots in each row
				if (canParkAt(row, column, c)) {// checks if the given spot is parkable for the given car
					park(row, column, c, timestamp);// parks the car at the given spot
					return true;// returns true since the car was successfully parked
				}
			}
		}
		return false;// returns false since the car was not successfully parked
	}

	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	public int getTotalCapacity() {
		int count = 0; // stores the total capacity of the parking lot
		for (int row = 0; row < numRows; row++)// iterates through the rows of the parking lot
			for (int column = 0; column < numSpotsPerRow; column++) // iterates through the spots in each row
				if (lotDesign[row][column] != CarType.NA)// checks if the given spot is a parking spot and increments the count if it is
					count++; // increments the count if the given spot is a parking spot

		return count; // returns the total capacity of the parking lot
	}

	/**
	 * @return the total occupancy of the parking lot
	 */
	public int getTotalOccupancy() {
		int count = 0; // stores the total occupancy of the parking lot
		for (int row = 0; row < numRows; row++) // iterates through the rows of the parking lot
			for (int column = 0; column < numSpotsPerRow; column++) // iterates through the spots in each row
				if (occupancy[row][column] != null) // checks if the given spot is occupied and increments the count if it is
					count++; // increments the count if the given spot is occupied

		return count; // returns the total occupancy of the parking lot
	}

	private void calculateLotDimensions(String strFilename) throws Exception {
		Scanner scanner = new Scanner(new File(strFilename)); // creates a scanner to read the file
		numRows = 0; // stores the number of rows in the parking lot
		numSpotsPerRow = 0; // stores the number of spots in each row of the parking lot

		while (scanner.hasNext()) {  // while loop for reading the lot design	
			String str = scanner.nextLine().trim(); // reads the next line of the file

			if (str.isEmpty()) {
				// Do nothing
			}
			else {
				String[] tokens = str.split(SEPARATOR); // splits the line into tokens
				numSpotsPerRow = tokens.length; // stores the number of spots in each row of the parking lot
				numRows++; // increments the number of rows in the parking lot
			}
		}

		scanner.close(); // closes the scanner
	}

	private void populateDesignFromFile(String strFilename) throws Exception {
		Scanner scanner = new Scanner(new File(strFilename)); // creates a scanner to read the file
		int lineCount = 0; // stores the number of lines in the file
		int rowNumber = 0; // stores the current row number

		// while loop for reading the lot design
		while (scanner.hasNext()) { // while loop for reading the lot design
			String str = scanner.nextLine().trim(); // reads the next line of the file
			lineCount++; // increments the number of lines in the file

			if (str.isEmpty()) {
				// Do nothing
			} 
			else {
				String[] tokens = str.split(SEPARATOR); // splits the line into tokens
				for (int i = 0; i < tokens.length; i++) // iterates through the tokens
					lotDesign[rowNumber][i] = Util.getCarTypeByLabel(tokens[i].trim()); // stores the car type at the given spot
				rowNumber++; // increments the current row number
			}
		}

		rowNumber = 0; // resets the current row number
		scanner.close(); // closes the scanner
	}

	/**
	 * NOTE: This method is complete; you do not need to change it.
	 * @return String containing the parking lot information
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("==== Lot Design ====").append(System.lineSeparator());

		for (int i = 0; i < lotDesign.length; i++) {
			for (int j = 0; j < lotDesign[0].length; j++) {
				buffer.append((lotDesign[i][j] != null) ? Util.getLabelByCarType(lotDesign[i][j])
						: Util.getLabelByCarType(CarType.NA));
				if (j < numSpotsPerRow - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(System.lineSeparator());
		}

		buffer.append(System.lineSeparator()).append("==== Parking Occupancy ====").append(System.lineSeparator());

		for (int i = 0; i < occupancy.length; i++) {
			for (int j = 0; j < occupancy[0].length; j++) {
				buffer.append(
						"(" + i + ", " + j + "): " + ((occupancy[i][j] != null) ? occupancy[i][j] : "Unoccupied"));
				buffer.append(System.lineSeparator());
			}

		}
		return buffer.toString();
	}
}
