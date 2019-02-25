package cs151.hw1;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author Khadeja Khalid
 * @version 1.0 2/16/2019
 * 
 * Defines a HashMap to hold an ArrayList of events, using the LocalDate date as the key
 */
public class MyCalendar 
{
	private HashMap<LocalDate, ArrayList<Event>> myCal;
	private LocalDate currentDate;
	
	/**
	 * Constructs a Calendar (myCal) using a HashMap with the LocalDate as the key
	 * and the value as an ArrayList of events
	 * Stores the given LocalDate as the current day
	 * @param cd - The day to start the calendar, usually today's date
	 */
	public MyCalendar(LocalDate cd)
	{
		myCal = new HashMap<LocalDate, ArrayList<Event>>();
		currentDate = cd;
	}
	
	/**
	 * Adds the event to the given LocalDate key's ArrayList
	 * If an ArrayList for the LocalDate key doesn't exist, create for it as well
	 * @param d - LocalDate key
	 * @param e - Event value
	 */
	public void addEvent(LocalDate d, Event e)
	{
		if(myCal.containsKey(d))
		{
			ArrayList<Event> events = myCal.get(d);
			events.add(e);
			myCal.put(d, events);
		}
		else
		{
			ArrayList<Event> events = new ArrayList<Event>();
			events.add(e);
			myCal.put(d, events);
		}
	}
	
	/**
	 * Removes a single event from the ArrayList, only if it's not regular
	 * @param d - LocalDate key to remove from
	 * @param e - Specific non-regular event to remove
	 * @return a boolean value if the event was removed or not
	 * precondition: The LocalDate key exists in the myCal HashMap
	 */
	public boolean removeOneTimeEventSpecific(LocalDate d, Event e)
	{
		boolean removed = false;
		if(myCal.containsKey(d))
		{
			ArrayList<Event> events = myCal.get(d);
			Event ev = findEvent(d, e.getName());
			if(!ev.getIsRegular())
				events.remove(e);
			myCal.put(d, events);
			
			if(events.isEmpty())
				myCal.remove(d);
			
			removed = true;
		}
		return removed;
	}	
	
	/**
	 * Removes all non-regular events on the given day (Only occurs once)
	 * @param d - LocalDate key to remove from
	 * @return a boolean value if the events were removed or not
	 * precondition: The LocalDate key exists in the myCal HashMap
	 */
	public boolean removeOneTimeEventAll(LocalDate d)
	{
		boolean removed = false;
		if(myCal.containsKey(d))
		{
			ArrayList<Event> events = myCal.get(d);
			
			java.util.Iterator<Event> itr = events.iterator();
			while(itr.hasNext())
			{
				Event e = itr.next();
				if(!e.getIsRegular())
					itr.remove();
			}
			
			myCal.put(d, events);
			
			if(events.isEmpty())
				myCal.remove(d);
			
			removed = true;
		}
		
		return removed;
	}	
	
	/**
	 * Removes a single regular event on the given day (Just that day)
	 * @param d - LocalDate key to remove from
	 * @param e - Specific regular event to remove
	 * @return a boolean value if the event was removed or not
	 * precondition: The LocalDate key exists in the myCal HashMap
	 */
	public boolean removeRegularEvent(LocalDate d, Event e)
	{
		boolean removed = false;
		if(myCal.containsKey(d))
		{
			ArrayList<Event> events = myCal.get(d);

			if(e.getIsRegular())
				events.remove(e);
			myCal.put(d, events);
			
			if(events.isEmpty())
				myCal.remove(d);
			
			removed = true;
		}
		
		return removed;
	}	
	
	/**
	 * Return the current day
	 * @return the currentDay the user is in
	 */
	public LocalDate currentDay()
	{
		return currentDate;
	}
	
	/**
	 * Move one day back from the current
	 * @return the previous day
	 */
	public LocalDate previousDay()
	{
		return currentDate = currentDate.minusDays(1);
	}
	
	/**
	 * Move one day forward from the current
	 * @return the next day
	 */
	public LocalDate nextDay()
	{
		return currentDate = currentDate.plusDays(1);
	}
	
	/**
	 * Move one month back from the current
	 * @return the previous month
	 */
	public LocalDate previousMonth()
	{
		return currentDate = currentDate.minusMonths(1);
	}
	
	/**
	 * Move one month forward from the current
	 * @return the next month
	 */
	public LocalDate nextMonth()
	{
		return currentDate = currentDate.plusMonths(1);
	}
	
	/**
	 * Find an event from the given name and date
	 * @param d - LocalDate key to look through
	 * @param name - Name of event to find
	 * @return the event if found, else null
	 * precondition: The LocalDate key exists in the myCal HashMap
	 */
	public Event findEvent(LocalDate d, String name)
	{
		Event event = null;
		if(!myCal.containsKey(d))
			return event;
		
		ArrayList<Event> events = myCal.get(d);
		for(Event ev : events)
		{
			if(name.equals(ev.getName()))
				event = ev;
		}
		return event;
	}
	
	/**
	 * Get the ArrayList of events, loop through, 
	 * and check if any overlap with the given event
	 * @param d - LocalDate key check with
	 * @param e - Event to check
	 * @return a boolean value if the events overlap or not
	 * precondition: The LocalDate key exists in the myCal HashMap
	 */
	public boolean timeOverlapCheck(LocalDate d, Event e)
	{
		boolean overlap = false;
		if(myCal.containsKey(d))
		{
			ArrayList<Event> events = myCal.get(d);
			for(Event ev : events)
			{
				if(ev.getTimeInterval().overlap(e))
					overlap = true;
			}
		}
		return overlap;
	}
	
	/**
	 * Returns a String of all the events in the given LocalDate in a formatted order
	 * @param c - LocalDate key to obtain events from
	 * @return a String of all the events for a specific day
	 */
	public String getEvents(LocalDate c)
	{
		String eventList = "";
		
		if(!myCal.containsKey(c))
			eventList = "No events listed!";
		else
		{
			sortEventsInOrderByTime(myCal.get(c));
			ArrayList<Event> events = myCal.get(c);
			
			for(Event e : events)
			{
				eventList = eventList + "  " + e.getName() + " | " + 
							e.getTimeInterval().startPrint() + " - " +
							e.getTimeInterval().endPrint() + "\n";
			}
		}
		
		return eventList;
	}
	
	/**
	 * Returns a String of all the regular events in the given 
	 * LocalDate in a formatted and sorted order
	 * @param c - LocalDate key to obtain events from
	 * @return a String of all the regular events for a specific day
	 */
	public String getRegEvents(LocalDate c)
	{
		String eventList = "";
		
		if(!myCal.containsKey(c))
			eventList = "No events listed!";
		else
		{
			sortEventsInOrderByTime(myCal.get(c));
			ArrayList<Event> events = myCal.get(c);
		
			for(Event e : events)
			{
				if(e.getIsRegular())
				{
					eventList = eventList + "  " + e.getName() + " | " + 
							e.getTimeInterval().startPrint() + " - " +
							e.getTimeInterval().endPrint() + "\n";
				}

			}
		}
		
		return eventList;
	}
	
	/**
	 * Returns a String of all the events in the myCal HashMap 
	 * in a formatted and sorted order
	 * @return a String of all the events in the myCal HashMap
	 */
	public String getEventListAll()
	{
		String eventList = new String();
		TreeMap<LocalDate, ArrayList<Event>> sortedMyCal = new TreeMap<>();
		sortedMyCal.putAll(myCal);
		
		for(LocalDate day : sortedMyCal.keySet())
		{
			ArrayList<Event> events = myCal.get(day);
			
			if (!eventList.contains(Integer.toString(day.getYear())))
				eventList = eventList + day.getYear() + "\n";

			eventList = eventList + "  " + day.getMonth() + "\n";
			
			eventList = eventList + "    " + day.getDayOfMonth() + " " + day.getDayOfWeek() + "\n";
			
			sortEventsInOrderByTime(events);
			for(Event e : events)
			{
				eventList = eventList + "      " + e.getName() + " | " + 
							e.getTimeInterval().startPrint() + " - " +
							e.getTimeInterval().endPrint() + "\n";
			}
		}
		
		return eventList;
	}
	
	/**
	 * Sorts the ArrayList of events in descending order by time using the
	 * Comparator interface, and comparing the start times
	 * @param events - An ArrayList of events to sort
	 */
	public void sortEventsInOrderByTime(ArrayList<Event> events)
	{
		class sortEventsByTime implements Comparator<Event>
		{
			public int compare(Event e1, Event e2) 
			{
				int event1 = e1.getTimeInterval().getStart();
				int event2 = e2.getTimeInterval().getStart();
				return Integer.compare(event1, event2);
			}
		}
		
		Collections.sort(events, new sortEventsByTime());
	}
	
	/**
	 * Prints the output in a formatted order to the PrintWriter object
	 * User must [Q]uit the program to obtain output
	 * @param out - A PrintWriter object to print to
	 */
	public void printOutput(PrintWriter out)
	{
		String eventList = new String();
		TreeMap<LocalDate, ArrayList<Event>> sortedMyCal = new TreeMap<>();
		sortedMyCal.putAll(myCal);
		
		for(LocalDate day : sortedMyCal.keySet())
		{
			ArrayList<Event> events = myCal.get(day);
			
			if (!eventList.contains(Integer.toString(day.getYear())))
			{
				eventList = eventList + day.getYear() + "\n";
				out.println(day.getYear());
			}
			
			eventList = eventList + "  " + day.getMonth() + "\n";
			out.println("  " + day.getMonth());
			
			eventList = eventList + "    " + day.getDayOfMonth() + " " + day.getDayOfWeek() + "\n";
			out.println("    " + day.getDayOfMonth() + " " + day.getDayOfWeek());
			
			sortEventsInOrderByTime(events);
			for(Event e : events)
			{
				eventList = eventList + "      " + e.getName() + " | " + 
							e.getTimeInterval().startPrint() + " - " +
							e.getTimeInterval().endPrint() + "\n";
				out.println("      " + e.getName() + " | " + 
						e.getTimeInterval().startPrint() + " - " +
						e.getTimeInterval().endPrint());
			}
		}
	}
	
	/**
	 * Prints a calendar in month view with the given LocalDate
	 * and highlights with brackets all day's that have an event 
	 * as well as the current day
	 * @param c - LocalDate to print from
	 */
    public void printCalendar(LocalDate c)
    {  
    	LocalDate beginning = LocalDate.of(c.getYear(), c.getMonth(), 1);
    	String firstDayString = beginning.getDayOfWeek().toString();
    	
    	int firstDayInt = 0;
		switch(firstDayString){
		case "SUNDAY":
			firstDayInt = 1;
			break;
		case "MONDAY":
			firstDayInt = 2;
			break;
		case "TUESDAY":
			firstDayInt = 3;
			break;
		case "WEDNESDAY":
			firstDayInt = 4;
			break;
		case "THURSDAY":
			firstDayInt = 5;
			break;
		case "FRIDAY":
			firstDayInt = 6;
			break;
		case "SATURDAY":
			firstDayInt = 7;
			break;
		}
    	
    	System.out.println("             " + c.getMonth() + " " + c.getYear());
    	System.out.println(" Su    Mo    Tu    We    Th    Fr    Sa");
    	
    	int weekday = 0;
    	//Print proper spaces for first day
    	for (int firstDaySpaces=1; firstDaySpaces<firstDayInt; firstDaySpaces++) 
    	{
            System.out.print("      ");
            weekday++;
        }
    	
    	LocalDate today = LocalDate.now();
    	
    	for (int day=1; day<=c.lengthOfMonth(); day++)
    	{
    		if ((day == today.getDayOfMonth() && c.getMonthValue() == today.getMonthValue() && c.getYear() == today.getYear())
    				|| myCal.containsKey(beginning))
    		{
        		if (day<10)
        			System.out.print(" [" + day + "]");
        		else
        			System.out.print("[" + day + "]"); 
        		weekday++;
        		if(weekday%7 == 0)
        			System.out.println();
        		else
        			System.out.print("  ");
    		}
    		else
    		{
        		if (day<10)
        			System.out.print("  " + day);
        		else
        			System.out.print(" " + day); 
        		weekday++;
        		if(weekday%7 == 0)
        			System.out.println();
        		else
        			System.out.print("   ");
    		}
    		
    		beginning = beginning.plusDays(1);
    	}
    	
    	System.out.println("\n");
    }
}
