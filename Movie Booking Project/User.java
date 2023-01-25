import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * This class is used to store the information about the user and complete user related tasks
 * @author Nicholas Stiner, Andrew Cheng, Silas DeLine, Griffin Wall
 * 
 */
public class User {
	private String userName;
	private String password;
	ArrayList<Movie> history = new ArrayList<Movie>();
	ArrayList<Movie> ratedMovies = new ArrayList<Movie>();
	Map<Ticket, Integer> tickets = new HashMap<Ticket, Integer>();
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * Add a ticket to the users ticket array
	 */
	public void addTicket(Ticket t, int qty) {
		tickets.put(t, qty);
	}
	
	/**
	 * Prints out a string of tickets which is in the users ticket array
	 */
	public void viewTickets() {
		for(Map.Entry<Ticket, Integer> t : tickets.entrySet()) {
			System.out.println(t.getKey().toString() + "\nQTY: " +  t.getValue());
		}
	}
	
	/**
	 * Gets the username of the user
	 * @return the username of the user
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the username of the user
	 * @param userName the username that is to be set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * Gets the password of the user
	 * @return the password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user
	 * @param password the new password given by the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addHistory(Movie obj)
	{
		
		this.history.add(obj);
	}
	
	public ArrayList<Movie> getHistory()
	{
		return this.history;
	}
	
	public void rateMovie(Movie movie, double rating) {
		Scanner sc = new Scanner(System.in);
		if(rating > 5 || rating < 0) {
			System.out.print("Invalid Rating. Please choose a number between 0 and 5...\n");
			rating = sc.nextInt();
		}
		if(hasRated(movie)) {
			System.out.println("You have already rated this movie");
		}
		else {
			movie.addRating(rating);
			this.ratedMovies.add(movie);
		}
		
	}
	
	public boolean hasRated(Movie movie) {
		if(this.ratedMovies.contains(movie)) {
			return true;
		}
		return false;
		
	}

	public void purchaseTicket(Movie m) {
		Scanner sc = new Scanner(System.in);
		System.out.printf("How many tickets would you like to purchase for %s?\n", m.getTitle());
		int qty = sc.nextInt();
		System.out.printf("Purchasing %d tickets... Thank you!\n", qty);
		Ticket ticket = new Ticket(m, this, qty);
		ticket.sendTicket();
		
		
	}
	
	
}
