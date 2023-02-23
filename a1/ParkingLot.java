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
	 * The delimiter that separates the parking lot design section from the parked
	 * car data section
	 */
	private static final String SECTIONER = "###";

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
	private Car[][] occupancy;

	/**
	 * Constructs a parking lot by loading a file
	 * 
	 * @param strFilename is the name of the file
	 */
	public ParkingLot(String strFilename) throws Exception {
		
		// determine numRows and numSpotsPerRow; you can do so by
		// writing your own code or alternatively by completing the
		// private calculateLotDimensions(...) that I provided
		calculateLotDimensions(strFilename); 
		
		//instantiate the lotDesign and occupancy variables!

		lotDesign = new CarType[numRows][numSpotsPerRow];
		occupancy = new Car[numRows][numSpotsPerRow]; // initializing the lotDesign and occupancy variables

		// populate lotDesign and occupancy; you can do so by
		// writing your own code or alternatively by completing the
		// private populateFromFile(...) that I provided
		populateFromFile(strFilename);
	}

	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @param c is the car to be parked
	 */
	public void park(int i, int j, Car c) {
		if(canParkAt(i, j, c)) { // checks if the car can park at the given location and parks it if it can
			occupancy[i][j] = c; // parks the car at the given location
		}
	}

	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the car removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Car remove(int i, int j) {
		if (i < 0 || i >= numRows || j < 0 || j >= numSpotsPerRow) { // checks if the given location is out of range
			return null;
		}
		if (occupancy[i][j] == null) { // checks if the given location is empty and returns null if it is
			return null;
		}
		Car car = occupancy[i][j]; // stores the car at the given location in a variable
		occupancy[i][j] = null; // removes the car from the given location
		return car; // returns the car that was removed
	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */

	public boolean canParkAt(int i, int j, Car c) {

		if (i < 0 || i >= numRows || j < 0 || j >= numSpotsPerRow) {// checks if the given location is out of range
			return false;
		}
		if (lotDesign[i][j] == CarType.NA) {// checks if the given location is not a parking spot and returns false if it is not
			return false;
		}
		if (occupancy[i][j] != null) {// checks if the given location is already occupied and returns false if it is
			return false;
		}
		if (c.getType() == CarType.ELECTRIC) {// checks if the given car is an electric car and returns true if it is parkable at the given spot
			return true;
		}
		if (c.getType() == CarType.SMALL) {// checks if the car is a small car and returns true if it is parkale at the given spot
			if (lotDesign[i][j] == CarType.SMALL || lotDesign[i][j] == CarType.REGULAR || lotDesign[i][j] == CarType.LARGE) {
				return true;
			}
		}
		if (c.getType() == CarType.REGULAR) {// checks if the car is a regular car and returns true if it is parkable at the given spot
			if (lotDesign[i][j] == CarType.REGULAR || lotDesign[i][j] == CarType.LARGE) {
				return true;
			}
		}
		if (c.getType() == CarType.LARGE) {// checks if the car is a large car and returns true if it is parkable at the given spot
			if (lotDesign[i][j] == CarType.LARGE) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	public int getTotalCapacity() {
		int capacity = 0; // initializes a variable to count the number of parking spots
		for (int row = 0; row < lotDesign.length; row++) { // loops through rows in the lotDesign array
			for (int column = 0; column < lotDesign[row].length; column++) { // loops through the columns of a row in the lotDesign array
				if (lotDesign[row][column] != CarType.NA) { // checks if the spot is a parking spot and increments the count variable if it is
					capacity++;
				}
			}
		}
		return capacity; // returns the number of parking spots
	}

	/**
	 * @return the total occupancy of the parking lot (i.e., the total number of
	 *         cars parked in the lot)
	 */
	public int getTotalOccupancy() {
		int spotsOccupied = 0; // initializes a variable to count the number of cars parked in the lot
		for (int row = 0; row < occupancy.length; row++) { // loops through the rows in the occupancy array
			for (int column = 0; column < occupancy[row].length; column++) {// loops through the columns of a row in the occupancy array
				if (occupancy[row][column] != null) { // checks if the spot is occupied and increments the count variable if it is
					spotsOccupied++;
				}
			}
		}
		return spotsOccupied; // returns the number of cars parked in the lot
	}

	private void calculateLotDimensions(String strFilename) throws Exception {
		
		Scanner scanner = new Scanner(new File(strFilename));

		while (scanner.hasNext()) {
			String line = scanner.nextLine().trim(); // trim() removes leading and trailing spaces
			if (line.isEmpty()) { // skip empty lines
				continue;
			}
			if (line.equals(SECTIONER)) { // check for the end of the lot design
				break;
			}
			numRows++; // increment the number of rows
			if (numSpotsPerRow == 0) { // set the number of spots per row if it is not set yet
				numSpotsPerRow = line.split(SEPARATOR).length; // split the line into an array of strings and get the length of the array
			}
		}
		scanner.close();
	}

	private void populateFromFile(String strFilename) throws Exception {
		
		Scanner scanner = new Scanner(new File(strFilename));

		int row= 0; // initialize a variable to keep track of the row index
		
		// while loop for reading lot design
		while (scanner.hasNext()) {
			String line = scanner.nextLine().trim(); // trim() removes leading and trailing spaces
			if (line.isEmpty()) { // skip empty lines
				continue;
			}
			if (line.equals(SECTIONER)) { // check for the end of the lot design
				break;
			}
			String[] parkingLotRow = line.split(SEPARATOR); // split the line into an array of strings
			for (int column = 0; column < parkingLotRow.length; column++) { // loop through the array of strings
				lotDesign[row][column] = Util.getCarTypeByLabel(parkingLotRow[column].trim()); // populate the lotDesign array
			}
			row++; // increment the row index
		}

		// while loop for reading occuppancy data
		while (scanner.hasNext()) {
			String line = scanner.nextLine().trim(); // trim() removes leading and trailing spaces
			if (line.isEmpty()) { // skip empty lines
				continue;
			}
			if (line.equals(SECTIONER)) { // check for the end of the lot design and begin reading occupancy data
				continue;
			}
			String[] carInfo = line.split(SEPARATOR); // split the line into an array of strings
			int lotRowIndex = Integer.parseInt(carInfo[0].trim()); // parse the row index
			int lotColumnIndex = Integer.parseInt(carInfo[1].trim()); // parse the spot index
			CarType type = Util.getCarTypeByLabel(carInfo[2].trim()); // parse the car type
			String plate = carInfo[3].trim(); // parse the plate number
			Car car = new Car(type, plate); // create a new car object

			if (canParkAt(lotRowIndex, lotColumnIndex, car)) { // check if the car can be parked at the given spot
				park(lotRowIndex, lotColumnIndex, car); // park the car at the given spot
			} 
			else { // if the car cannot be parked at the given spot
				System.out.println("Car " +Util.getLabelByCarType(type)+"("+  car.getPlateNum() + ")"+" cannot be parked at (" + lotRowIndex + ", "
						+ lotColumnIndex + ")");
			} // print an error message
		}
		scanner.close();
	}
		
	/**
	 * Produce string representation of the parking lot
	 * 
	 * @return String containing the parking lot information
	 */
	public String toString() {
		// NOTE: The implementation of this method is complete. You do NOT need to
		// change it for the assignment.
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

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the file to process. Next, it creates an instance of
	 * ParkingLot. Finally, it prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();

		System.out.print("Please enter the name of the file to process: ");

		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();

		ParkingLot lot = new ParkingLot(strFilename);

		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		System.out.println("Number of cars currently parked in the lot: " + lot.getTotalOccupancy());

		System.out.print(lot);

	}
}
