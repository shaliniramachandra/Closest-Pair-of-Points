/**
 * This class file is used to implement the File Input Parser,
 * that parses the given input file and gets the total number of points,
 * the point's x and y co-ordinates.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class parses the input file, and creates an object array to store all
 * the points.
 * 
 * @author Shalini Ramachandra #1465487
 * 
 */
public class FileInputParser {
	private static final String spaceDelimiter = "\\s+";
	private String filepath;
	private int numberOfPoints;
	private Point[] points;

	/**
	 * /** This constructor sets the value for the class variables.
	 * 
	 * @param filePath
	 *            - The input file, which has to be parsed.
	 */

	public FileInputParser(final String filePath) {

		if (filePath == null || filePath.length() <= 0) {
			throw new IllegalArgumentException(
					"File path cannot be null or empty");
		}

		this.filepath = filePath;
		this.numberOfPoints = -1;
		this.points = null;
	}

	/**
	 * This method returns the total number of points (n).
	 * 
	 * @return - Returns number of points.
	 */
	public int getNumberOfPoints() {
		if (this.numberOfPoints != -1)
			return this.numberOfPoints;
		else {
			this.parseFile();
			return this.numberOfPoints;
		}

	}

	/**
	 * This method returns the point with its x and y co-ordinates.
	 * 
	 * @param index
	 *            - The index of the point, whose x and y co-ordinates are
	 *            required.
	 * @return
	 */
	public Point getPoint(final int index) {
		if (this.points[index] != null)
			return this.points[index];
		else {
			this.parseFile();
			return this.points[index];
		}
	}

	/**
	 * This method is used to parse the input file get the total number points
	 * and the points array.
	 * 
	 */
	private void parseFile() {

		Exception exception = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(this.filepath);
			bufferedReader = new BufferedReader(fileReader);

			this.numberOfPoints = Integer.parseInt(bufferedReader.readLine()
					.trim());
			this.points = new Point[this.numberOfPoints];

			setPoints(this.points, bufferedReader);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file path or name.");
			exception = e;
		} catch (NumberFormatException e) {
			System.out
					.println("Invalid data in input file, number is expected.");
			exception = e;
		} catch (IOException e) {
			System.out.println("Cannot read the file, I/O Exception occurred.");
			exception = e;
		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}

				if (bufferedReader != null) {
					bufferedReader.close();
				}

			} catch (IOException e) {
				System.out.println("Cannot close file or buffered reader.");
				exception = e;
			}

			if (exception != null) {
				System.exit(1);
			}
		}

	}

	/**
	 * This method is used to parse the input file and then set the point's x
	 * and co-ordinate in the object array.
	 * 
	 * @param points
	 *            - Points array, which is used to set a point's corresponding x
	 *            and y co-ordinates.
	 * @param bufferedReader
	 *            - Buffered reader object reads from the input file.
	 * @throws IOException
	 *             - Handles I/O Exception if thrown by the method.
	 */
	private void setPoints(final Point[] points,
			final BufferedReader bufferedReader) throws IOException {

		String[] pointsAsStringArray = new String[this.numberOfPoints];

		// Parsing the input points as string.
		for (int i = 0; i < this.numberOfPoints; i++) {
			pointsAsStringArray[i] = bufferedReader.readLine();
		}

		// Converts the points which was read as string to Double and then
		// sets the points in the points array
		for (int i = 0; i < pointsAsStringArray.length; i++) {

			String pointString = pointsAsStringArray[i];
			String[] splitArray = pointString.trim().split(spaceDelimiter);
			this.points[i] = new Point(Double.parseDouble(splitArray[0]),
					Double.parseDouble(splitArray[1]));
		}

	}
}
