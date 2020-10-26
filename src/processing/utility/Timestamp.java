package processing.utility;

import java.util.Date;

/**
 * Class Representing a Timestamp
 *
 * @author Ahmed Zaheer Dadarkar
 */

public class Timestamp implements Comparable<Timestamp> {
	/*
	 * Timestamp is internally stored as a Date
	 */
	private Date date;
	
	/*
	 * Timestamp Constructor
	 * 
	 * @param date Date to build the timestamp
	 */
	public Timestamp(Date date) {
		this.date = date;
	}
	
	/*
	 * Converts to String
	 * 
	 * @return Timestamp as a String
	 */
	public String toString() {
		return date.toString();
	}
	
	/*
	 * Converts to Date
	 * 
	 * @return Timestamp as a Date
	 */
	public Date toDate() {
		return date;
	}
	
	/*
	 * Equals Method
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Timestamp) {
			Timestamp timestamp = (Timestamp)obj;
			return date.equals(timestamp.date);
		}
		else
			return false;
	}

	/*
	 * Compare Method
	 */
	@Override
	public int compareTo(Timestamp timestamp) {
		return date.compareTo(timestamp.date);
	}
}