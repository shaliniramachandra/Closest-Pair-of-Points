/**
 * This class is used to find the distance between the closest pair of points
 */
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class file computes the distance between the closest pair of points, by
 * implementing closest pair of points algorithm.
 * 
 * @author Shalini Ramachandra #1465487
 * 
 */
public class ClosestPair {

	final private static int NUMBER_OF_NEIGHBORS_TO_CHECK_IN_SY = 7;
	final private static int CONSTANT_FACTOR_TO_APPLY_BRUTE_FORCE = 3;

	/**
	 * This method is used to get the number points, and the points array. This
	 * method calls the closest pair recursion method, which finds the minimum
	 * distance between the closest points
	 * 
	 * @param parser
	 *            - The parser object, which contains the total number of points
	 *            (n) and the points array.
	 * @return - Returns the distance between the closest pair of points
	 */
	public double findClosestPair(final FileInputParser parser) {

		int numberOfPoints = parser.getNumberOfPoints();
		Point[] pointsSortedByXCoOrdinate = new Point[numberOfPoints];
		Point[] pointsSortedByYCoOrdinate = new Point[numberOfPoints];

		// Performs a deep copy of the points.
		for (int i = 0; i < numberOfPoints; i++) {
			pointsSortedByXCoOrdinate[i] = parser.getPoint(i);
			pointsSortedByYCoOrdinate[i] = parser.getPoint(i);
		}

		// Sorts the point according to x and y co-ordinates.
		Arrays.sort(pointsSortedByXCoOrdinate, new PointXCoOrdinateComparator());
		Arrays.sort(pointsSortedByYCoOrdinate, new PointYCoOrdinateComparator());
		return closestPairRecursion(pointsSortedByXCoOrdinate,
				pointsSortedByYCoOrdinate, 0);
	}

	/**
	 * This method recursively computes the distance between the closest pair of
	 * points.
	 * 
	 * @param pointsSortedByXCoOrdinate
	 *            - Points array sorted by X co-ordinate.
	 * @param pointsSortedByYCoOrdinate
	 *            - Points array sorted by Y co-ordinate.
	 * @param startIndex
	 *            - Start index of the array.
	 * @return - Returns the distance between the closest pair of points.
	 */
	private double closestPairRecursion(
			final Point[] pointsSortedByXCoOrdinate,
			final Point[] pointsSortedByYCoOrdinate, final int startIndex) {
		int endIndex = startIndex + pointsSortedByXCoOrdinate.length - 1;

		// Applying brute force if number of points is less than 3
		if (pointsSortedByXCoOrdinate.length <= CONSTANT_FACTOR_TO_APPLY_BRUTE_FORCE) {

			double distanceBetweenPoints = applyBruteForce(pointsSortedByXCoOrdinate);
			System.out.format("D[%d,%d]: %.4f%n", startIndex, endIndex,
					distanceBetweenPoints);
			return distanceBetweenPoints;

		}

		// Compute Q and R - Divide the array into two parts, left and right
		int numberOfPointsInQ = (int) Math
				.ceil((double) pointsSortedByXCoOrdinate.length / 2);
		int numberOfPointsInR = (int) Math
				.floor((double) pointsSortedByXCoOrdinate.length / 2);
		Point[] setOfPointsInQ = new Point[numberOfPointsInQ];
		Point[] setOfPointsInR = new Point[numberOfPointsInR];
		for (int i = 0; i < numberOfPointsInQ; i++)
			setOfPointsInQ[i] = pointsSortedByXCoOrdinate[i];
		for (int i = 0; i < numberOfPointsInR; i++) {
			setOfPointsInR[i] = pointsSortedByXCoOrdinate[numberOfPointsInQ + i];
		}

		// Compute Qx, Qy, Rx, Ry
		Point[] pointsInQSortedByX = getCopySortedByX(setOfPointsInQ);
		Point[] pointsInQSortedByY = getCopySortedByY(setOfPointsInQ);
		Point[] pointsInRSortedByX = getCopySortedByX(setOfPointsInR);
		Point[] pointsInRSortedByY = getCopySortedByY(setOfPointsInR);

		// Recursively call to compute the closest distance
		double distanceBetweenClosestPointsInQ = closestPairRecursion(
				pointsInQSortedByX, pointsInQSortedByY, startIndex);
		double distanceBetweenClosestPointsInR = closestPairRecursion(
				pointsInRSortedByX, pointsInRSortedByY, startIndex
						+ setOfPointsInQ.length);

		// Find delta
		double delta = Math.min(distanceBetweenClosestPointsInQ,
				distanceBetweenClosestPointsInR);
		double xStar = pointsInQSortedByX[numberOfPointsInQ - 1].getX();

		// Points in P within distance delta of Xstar and sorted by Y, Sy array.
		ArrayList<Point> pointsInSSortedByY = new ArrayList<Point>();
		for (int i = 0; i < pointsSortedByYCoOrdinate.length; i++) {
			if (Math.abs(xStar - pointsSortedByYCoOrdinate[i].getX()) < delta) {
				pointsInSSortedByY.add(pointsSortedByYCoOrdinate[i]);
			}
		}

		// Checking the 2*delta range, while combining Q and R
		double distanceBetweenSAndNeighbor = 0.0;
		double minDistanceBetweenSAndNeighbor = Double.POSITIVE_INFINITY;
		for (int i = 0; i < pointsInSSortedByY.size(); i++) {
			for (int j = i + 1; j <= i + NUMBER_OF_NEIGHBORS_TO_CHECK_IN_SY
					&& j < pointsInSSortedByY.size(); j++) {
				distanceBetweenSAndNeighbor = calculateEuclideanDistance(
						pointsInSSortedByY.get(i), pointsInSSortedByY.get(j));
				if (minDistanceBetweenSAndNeighbor > distanceBetweenSAndNeighbor)
					minDistanceBetweenSAndNeighbor = distanceBetweenSAndNeighbor;
			}
		}

		// Computes the minimum distance
		double distanceBetweenClosestPoints = Math.min(
				minDistanceBetweenSAndNeighbor, delta);
		System.out.format("D[%d,%d]: %.4f%n", startIndex, endIndex,
				distanceBetweenClosestPoints);
		return distanceBetweenClosestPoints;

	}

	/**
	 * This method is used to calculate the Euclidean distance between the two
	 * points.
	 * 
	 * @param point1
	 *            - Point 1 with its x and y co-ordinates.
	 * @param point2
	 *            - Point 2 with its x and y co-ordinates.
	 * @return - Returns the Euclidean distance between the two points.
	 */
	private double calculateEuclideanDistance(Point point1, Point point2) {

		double distance;
		double xDifference = point1.getX() - point2.getX();
		double yDifference = point1.getY() - point2.getY();
		double xSquare = Math.pow(xDifference, 2);
		double ySquare = Math.pow(yDifference, 2);
		distance = Math.sqrt(xSquare + ySquare);
		return distance;
	}

	/**
	 * Apply brute force technique to determine the closest distance when there
	 * are few points
	 * 
	 * @param points
	 *            - Points array on which the brute force will be applied to
	 *            compute the Euclidean distances.
	 * @return - Returns the minimum distance among the set of points.
	 */
	private double applyBruteForce(Point[] points) {

		double distanceBetweenPoints;
		double minDistanceBetweenPoints = Double.POSITIVE_INFINITY;

		// The order of this is always a constant, as the length of
		// pointsSortedByXCoOrdinate is either 2 or 3.
		for (int i = 0; i < (points.length - 1); i++) {
			for (int j = i + 1; j < points.length; j++) {
				distanceBetweenPoints = calculateEuclideanDistance(points[i],
						points[j]);
				if (distanceBetweenPoints < minDistanceBetweenPoints)
					minDistanceBetweenPoints = distanceBetweenPoints;
			}
		}
		return minDistanceBetweenPoints;
	}

	/**
	 * This method is used to create a copy of the points array and then sort it
	 * according to the X co-ordinate.
	 * 
	 * @param points
	 *            - Array of points that will copied into another array and sort
	 *            the new array according to X co-ordinate.
	 * @return - Returns the sorted array
	 */
	private Point[] getCopySortedByX(Point[] points) {
		Point[] pointsAfterSorting = getCopy(points);
		Arrays.sort(pointsAfterSorting, new PointXCoOrdinateComparator());
		return pointsAfterSorting;
	}

	/**
	 * This method is used to create a copy of the points array and then sort it
	 * according to the Y co-ordinate.
	 * 
	 * @param points
	 *            - Array of points that will be copied into another array and
	 *            sort the new array according to Y co-ordinate.
	 * @return - Returns the sorted array
	 */
	private Point[] getCopySortedByY(Point[] points) {
		Point[] pointsAfterSorting = getCopy(points);
		Arrays.sort(pointsAfterSorting, new PointYCoOrdinateComparator());
		return pointsAfterSorting;
	}

	/**
	 * This method is used to create a copy of the array which will be passed.
	 * 
	 * @param points
	 *            - Array of points that will be copied into another array.
	 * @return - Copied array.
	 */
	private Point[] getCopy(Point[] points) {
		Point[] copy = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			copy[i] = points[i];
		}
		return copy;
	}
}
