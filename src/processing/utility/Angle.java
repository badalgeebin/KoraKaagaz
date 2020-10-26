package processing.utility;

/**
 * Class Representing an Angle
 * 
 * @author Ahmed Zaheer Dadarkar
 */

public class Angle {
	public double angle;
	
	/**
	 * Angle Constructor
	 * 
	 * @param angle Angle as a double value
	 */
	public Angle(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Equals Method
	 */
	@Override
	public boolean equals(Object otherAngle) {
		if(otherAngle instanceof Angle)
			return angle == ((Angle)otherAngle).angle;
		else
			return false;
	}
}
