/**
 * This class file defines fields and the methods, which every point will have.
 */

/**
 * 
 * This class defines the structure of point, i.e., it's x and y co-ordinates
 * and the method which will be used to get the x and y co-ordinates of a point.
 * 
 * @author Shalini Ramachandra #1465487
 * 
 */
public class Point {
	private Double x;
	private Double y;

	/**
	 * This method is used to set the x and y co-ordinates of a point.
	 * 
	 * @param x
	 *            - The x co-ordinate of a point.
	 * @param y
	 *            - The y co-ordinate of a point.
	 */
	public Point(final Double x, final Double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * This method is used to get the x co-ordinate of a point
	 * 
	 * @return - Return the x co-ordinate value
	 */
	public Double getX() {
		return this.x;
	}

	/**
	 * This method is used to get the y co-ordinate of a point
	 * 
	 * @return - Return the y co-ordinate value
	 */
	public Double getY() {
		return this.y;
	}

}
