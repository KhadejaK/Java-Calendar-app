package cs151.hw1;

/**
 * @author Khadeja Khalid
 * @version 1.0 2/16/2019
 *
 * The TimeInterval classvrepresents an interval of time, suitable for events 
 * (such as a meeting on a given date from 10:00 - 11:00). 
 * Includes a method to check whether two intervals overlap.
 */
public class TimeInterval 
{
	private int startTime;
	private int endTime;
	private String startTimeString;
	private String endTimeString;
	
	/**
	 * Constructs a new TimeInterval with the a given start and end time
	 * @param s - Start Time as an integer
	 * @param e - End Time as an integer
	 * @param ss - Start time as a String representation
	 * @param es - End time as a String representation
	 */
	public TimeInterval(int s, int e, String ss, String es)
	{
		startTime = s;
		endTime = e;
		startTimeString = ss;
		endTimeString = es;
	}
	
	/**
	 * Returns the Start Time as an integer
	 * @return the Start Time
	 */
	public int getStart()
	{
		return startTime;
	}
	
	/**
	 * Returns the Start Time as a String
	 * @return the Start Time's String representation
	 */
	public String startPrint()
	{
		return startTimeString;
	}
	
	/**
	 * Returns the End Time as an integer
	 * @return the End Time
	 */
	public int getEnd()
	{
		return endTime;
	}
	
	/**
	 * Returns the End Time as a String
	 * @return the End Time as a String representation
	 */
	public String endPrint()
	{
		return endTimeString;
	}
	
	/**
	 * Checks to see if two event's TimeInterval's overlap
	 * If the Start Time or the End Time is in between another's, return true
	 * @param e - Event to check with
	 * @return a boolean value if the event's time overlaps or not
	 */
	public boolean overlap(Event e)
	{
		boolean overlap = false;
		
		if(e.getTimeInterval().getStart() >= getStart() && e.getTimeInterval().getStart() <= getEnd())
			overlap = true;
		else if(e.getTimeInterval().getEnd() >= getStart() && e.getTimeInterval().getEnd() <= getEnd())
			overlap = true;
		else if (e.getTimeInterval().getStart() == getStart() || e.getTimeInterval().getStart() == getEnd()
			     || e.getTimeInterval().getEnd() == getStart() || e.getTimeInterval().getEnd() == getEnd())
			overlap = true;
		
		return overlap;
	}
}
