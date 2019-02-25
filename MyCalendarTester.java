package cs151.hw1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * @author Khadeja Khalid
 * @version 1.0 2/16/2019
 *
 * MyCalendarTester Class creates a new MyCalendar object and offer's a variety of
 * options to use, such as adding an event, removing an event, reading from a file,
 * viewing events, days, and months, etc. and prints to an output file after program
 * has been [Q]uit
 */
public class MyCalendarTester 
{
	public static void main(String[] args) throws IOException
	{
		LocalDate today = LocalDate.now();
		MyCalendar myCal = new MyCalendar(today);
		
		myCal.printCalendar(myCal.currentDay());
		
		FileReader file = new FileReader("events.txt");
		file.fileReader(myCal);
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Select one of the following options: \n" +
					"[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit \n");
		
		String choice = input.next();
		String choiceTwo = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
		
		while (!choice.equalsIgnoreCase("Q"))
		{
			switch(choice.toUpperCase()){
			case "V":
				System.out.println("[D]ay view or [M]view ?");
				choiceTwo = input.next();
				
				while(!choiceTwo.equalsIgnoreCase("G"))
				{
					switch(choiceTwo.toUpperCase()){
					case "D":
						System.out.println(formatter.format(myCal.currentDay()));
						System.out.println(myCal.getEvents(myCal.currentDay()));
						System.out.println("[P]revious or [N]ext or [G]o back to main menu ?");
						choiceTwo = input.next();
						
						while(!choiceTwo.equalsIgnoreCase("G"))
						{
							switch(choiceTwo.toUpperCase()){
							case "P":
								myCal.previousDay();
								System.out.println(formatter.format(myCal.currentDay()));
								System.out.println(myCal.getEvents(myCal.currentDay()));
								break;
							case "N":
								myCal.nextDay();
								System.out.println(formatter.format(myCal.currentDay()));
								System.out.println(myCal.getEvents(myCal.currentDay()));
								break;
							}
							System.out.println("[P]revious or [N]ext or [G]o back to main menu ?");
							choiceTwo = input.next();
						}
						break;
					case "M":
						myCal.printCalendar(myCal.currentDay());
						System.out.println("[P]revious or [N]ext or [G]o back to main menu ?");
						choiceTwo = input.next();
						
						while(!choiceTwo.equalsIgnoreCase("G"))
						{
							switch(choiceTwo.toUpperCase()){
							case "P":
								myCal.previousMonth();
								myCal.printCalendar(myCal.currentDay());
								break;
							case "N":
								myCal.nextMonth();
								myCal.printCalendar(myCal.currentDay());
								break;
							}
							System.out.println("[P]revious or [N]ext or [G]o back to main menu ?");
							choiceTwo = input.next();
						}
						break;
					}
				}
				break;
			
			case "C":
				System.out.print("Name: ");
				input.nextLine();
				String name = input.nextLine();
				
				System.out.print("Date [dd/mm/yyyy]: ");
				String date = input.next();
				String[] day = date.split("/");
				if(day[2].length() == 2)
					day[2] = "20" + day[2];
				
				System.out.print("Start [24hr]: ");
				String start = input.next();
				String startT = start.replaceAll(":", "");
				int startTime = Integer.parseInt(startT);
				
				System.out.print("End [24hr]: ");
				String end = input.next();
				String endT = end.replaceAll(":", "");
				int endTime = Integer.parseInt(endT);
				
				TimeInterval timeInterval = new TimeInterval(startTime, endTime, start, end);
				Event event = new Event(name, timeInterval, false);
				LocalDate newDate = LocalDate.of(Integer.parseInt(day[2]), Integer.parseInt(day[0]), Integer.parseInt(day[1]));
				
				if(!myCal.timeOverlapCheck(newDate, event))
				{
					myCal.addEvent(newDate, event);
					System.out.println("Created! " + "\n"  + myCal.getEvents(newDate));
				}
				else
				{
					System.out.println("Event not created. Time conflict. \n");
				}
				break;
			
			case "G":
				System.out.print("Enter the date to go to [dd/mm/yyyy]: ");
				date = input.next();
				day = date.split("/");
				if(day[2].length() == 2)
					day[2] = "20" + day[2];
				
				newDate = LocalDate.of(Integer.parseInt(day[2]), Integer.parseInt(day[0]), Integer.parseInt(day[1]));
				
				System.out.println(formatter.format(newDate));
				System.out.println(myCal.getEvents(newDate));
				break;
			
			case "E":
				System.out.println(myCal.getEventListAll());
				break;
			
			case "D":
				System.out.print("[S]elected, [A]ll, or [DR]egular ? ");
				choiceTwo = input.next();
				
				switch(choiceTwo.toUpperCase()){
				case "S":
					System.out.print("Enter the date to delete one event [dd/mm/yyyy]: ");
					date = input.next();
					day = date.split("/");
					if(day[2].length() == 2)
						day[2] = "20" + day[2];
					newDate = LocalDate.of(Integer.parseInt(day[2]), Integer.parseInt(day[0]), Integer.parseInt(day[1]));
					
					System.out.println(formatter.format(newDate));
					System.out.println(myCal.getEvents(newDate));
					
					if(!myCal.getEvents(newDate).equals("No events listed!"))
					{
						System.out.print("Enter the name of the event to delete: ");
						input.nextLine();
						String name2 = input.nextLine();
						
						Event e = myCal.findEvent(newDate, name2);
								
						boolean removed = myCal.removeOneTimeEventSpecific(newDate, e);
						
						if(removed == true)
						{
							System.out.println("The event is deleted. Here is the current scheduled event: ");
							System.out.println(formatter.format(newDate));
							System.out.println(myCal.getEvents(newDate));
						}
						else
							System.out.println("The event was not deleted.");
					}
					break;
					
				case "A":
					System.out.print("Enter the date to delete all events [dd/mm/yyyy]: ");
					date = input.next();
					day = date.split("/");
					if(day[2].length() == 2)
						day[2] = "20" + day[2];
					newDate = LocalDate.of(Integer.parseInt(day[2]), Integer.parseInt(day[0]), Integer.parseInt(day[1]));
							
					boolean removed2 = myCal.removeOneTimeEventAll(newDate);
					if(removed2 == true)
					{
						System.out.println("The non-regular events are deleted: ");
						System.out.println(formatter.format(newDate));
						System.out.println(myCal.getEvents(newDate));
					}
					else
						System.out.println("The events were not deleted.");
					break;
					
				case "DR":
					System.out.print("Enter the date to delete reg event [dd/mm/yyyy]: ");
					date = input.next();
					day = date.split("/");
					if(day[2].length() == 2)
						day[2] = "20" + day[2];
					newDate = LocalDate.of(Integer.parseInt(day[2]), Integer.parseInt(day[0]), Integer.parseInt(day[1]));
					
					System.out.println(formatter.format(newDate));
					System.out.println(myCal.getRegEvents(newDate));
					
					if(!myCal.getRegEvents(newDate).equals(""))
					{
						System.out.print("Enter the name of the regular event to delete: ");
						input.nextLine();
						name = input.nextLine();
						
						Event e = myCal.findEvent(newDate, name);
								
						boolean removed3 = myCal.removeRegularEvent(newDate, e);
						if(removed3 == true)
						{
							System.out.print("The event is deleted. Here are the current scheduled events: ");
							System.out.println(formatter.format(newDate));
							System.out.println(myCal.getEvents(newDate));
						}
						else
							System.out.println("The event was not deleted.");
					}
					else
						System.out.print("No Regular Events!");
					break;
				}
				
				break;
			
			default: 
				if(!choice.equalsIgnoreCase("Q"))
					System.out.println("Invalid, please try again");
				break;
			}
			
			System.out.print("Select one of the following options: \n" +
					"[V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit \n");
			choice = input.next();
		}
		
			
		System.out.println("Good Bye!");
        input.close();
        
        PrintWriter out = new PrintWriter(new File("C://cs151//hw1//output.txt"), "UTF-8");
        myCal.printOutput(out);
        out.close();

	}
}
