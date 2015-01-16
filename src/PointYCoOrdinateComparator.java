/**
 * This class is used to define the compare method in Arrays.sort, which will 
 * sort the points in the increasing order of y co-ordinates.
 */

import java.util.Comparator;

/**
 * 
 * This class implements the Comparator interface and it defines the
 * implementation for compare method.
 * 
 * @author Shalini Ramachandra #1465487
 * 
 */
public class PointYCoOrdinateComparator implements Comparator<Point> {
	/**
	 * This method defines the compare definition for the points to be sorted
	 * based on the x co-ordinates.
	 * 
	 * @return - Returns an integer value based on the comparison result.
	 */
	@Override
	public int compare(final Point point1, final Point point2) {
		if (point1.getY() > point2.getY()) {
			return 1;
		} else if (point1.getY() < point2.getY()) {
			return -1;
		} else {
			return 0;
		}
	}

}
