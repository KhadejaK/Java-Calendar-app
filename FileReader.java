package cs151.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * @author Khadeja Khalid
 * @version 1.0 2/16/2019
 *
 * A FileReader class to read the input file, and break down it's components into 
 * their respective classes, and load the calendar with preset events 
 */
public class FileReader 
{
	private String fileName = "";
	
	/**
	 * Constructs a FileReader with the given name of the file
	 * @param name - Name of the file
	 */
	public FileReader(String name)
	{
		fileName = name;
	}
	
	/**
	 * Reads the file, breaks down the content, and loads into the MyCalander class
	 * @param myCal - MyCalender object to load contents into
	 * @throws FileNotFoundException - If the file's not found, throw an exception
	 */
    public void fileReader(MyCalendar myCal) throws FileNotFoundException
    {
    	File events = new File(fileName);
		Scanner input = new Scanner(events);
		TimeInterval timeInterval;
		Event event;
		
		while (input.hasNextLine()) 
		{
			String eventName = input.nextLine();
			String thing = input.next().toUpperCase();
			if (Character.isLetter(thing.charAt(0)))
			{
				String[] days = thing.split("");
				
				String startTS = input.next();
				String startT = startTS.replaceAll(":", "");
				int startTime = Integer.parseInt(startT);
				
				String endTS = input.next();
				String endT = endTS.replaceAll(":", "");
				int endTime = Integer.parseInt(endT);
				
				timeInterval = new TimeInterval(startTime, endTime, startTS, endTS);
				
				String[] startD = input.next().split("/");
				if(startD[2].length() == 2)
					startD[2] = "20" + startD[2];
				
				String[] endD = input.next().split("/");
				if(endD[2].length() == 2)
					endD[2] = "20" + endD[2];
				
				event = new Event(eventName, timeInterval, true);
				
				LocalDate newDateS = LocalDate.of(Integer.parseInt(startD[2]), Integer.parseInt(startD[0]), Integer.parseInt(startD[1]));
				//Year, month, day
				LocalDate newDateE = LocalDate.of(Integer.parseInt(endD[2]), Integer.parseInt(endD[0]), Integer.parseInt(endD[1]));
				
				int dayOfWeek = 0;
				
				while(!newDateS.isEqual(newDateE))
				{
					for(int x=0; x<days.length; x++)
					{
						switch(days[x]){
						case "M":
							dayOfWeek = 1;
							break;
						case "T":
							dayOfWeek = 2;
							break;
						case "W":
							dayOfWeek = 3;
							break;
						case "R":
							dayOfWeek = 4;
							break;
						case "F":
							dayOfWeek = 5;
							break;
						case "A":
							dayOfWeek = 6;
							break;
						case "S":
							dayOfWeek = 7;
							break;
							
						}
						
						if(newDateS.getDayOfWeek().getValue() == dayOfWeek)
						{
							myCal.addEvent(newDateS, event);
						}
					}
					newDateS = newDateS.plusDays(1);
				}
					
				if (input.hasNextLine()) 
					input.nextLine();
			}
			else 
			{
				String[] day = thing.split("/");
				if(day[2].length() == 2)
					day[2] = "20" + day[2];
				
				String startTS = input.next();
				String startT = startTS.replaceAll(":", "");
				int startTime = Integer.parseInt(startT);
				
				String endTS = input.next();
				String endT = endTS.replaceAll(":", "");
				int endTime = Integer.parseInt(endT);
				
				timeInterval = new TimeInterval(startTime, endTime, startTS, endTS);
				
				event = new Event(eventName, timeInterval, false);
				
				LocalDate newDate = LocalDate.of(Integer.parseInt(day[2]), Integer.parseInt(day[0]), Integer.parseInt(day[1]));
				
				myCal.addEvent(newDate, event);
				
				if (input.hasNextLine()) 
					input.nextLine();
			}
		}
		System.out.println("Loading is done! \n");
		input.close();
    }
}
