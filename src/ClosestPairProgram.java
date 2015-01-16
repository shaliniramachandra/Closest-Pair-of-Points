/**
 * This is the main class file for this project which implements the closest pair of 
 * points algorithm, and gives the closest distance between given set of points.
 */

/**
 * This is the main program for implementation of the closest pair of points
 * algorithm.
 * 
 * @author Shalini Ramachandra #1465487
 * 
 */
public class ClosestPairProgram {

	/**
	 * This is the main program for implementation of the closest pair of points
	 * algorithm.
	 * 
	 * @param args
	 *            - Command line arguments passed. This is not used in our
	 *            program.
	 */
	public static void main(String[] args) {

		FileInputParser parser = new FileInputParser("program2data.txt");
		parser.getNumberOfPoints();
		ClosestPair closestPair = new ClosestPair();
		closestPair.findClosestPair(parser);
	}

}
