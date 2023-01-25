// Class to create and send tickets and to return information about the ticket
public class Ticket {
	private Movie movie;
	private User user;
	private int qty;

	
	// Movie and user data
	Ticket(Movie movie, User user, int qty) {
		this.movie = movie;
		this.user = user;
		this.qty = qty;
	}
	// Method used to create the ticket
	// Method to send the ticket
	public void sendTicket() {
		this.user.addTicket(this, this.qty);
	}
	// Returns the title, the genre, the time, and the location of the movie
	public String toString() {
		return "Title: " + movie.getTitle() + "\n" + 
				"Genre: " + movie.getGenre() + "\n"  +
				"Time: " + movie.getTime() + "\n"  +
				"Location: " + movie.getLocation() + "\n" +
				"Price: " + movie.getPrice();
		
	}
	
}
