package cs151.hw1;
/**
 * @author Khadeja Khalid
 * @version 1.0 2/16/2019
 * 
 * Represents an event. 
 * An event consists of its Name and a TimeInterval of the event.
 */
public class Event 
{
	private String name;
	private TimeInterval timeInterval;
	private boolean isRegular;
	
	/**
	 * Constructs an event with the given name, time interval, 
	 * and if it's a regular event or not
	 * @param n - name of event
	 * @param tI - time interval (24 hrs)
	 * @param isR - is the event regular or not
	 */
	public Event(String n, TimeInterval tI, boolean isR)
	{
		name = n;
		timeInterval = tI;
		isRegular = isR;
	}
	
	/**
	 * Returns the name of the given event
	 * @return the name of the event
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the time interval class for the given event
	 * @return the time interval of the class
	 */
	public TimeInterval getTimeInterval()
	{
		return timeInterval;
	}
	
	/**
	 * Returns a boolean value if the event given is a regular even or not
	 * @return true or false if the event is a regular event
	 */
	public boolean getIsRegular()
	{
		return isRegular;
	}
}
