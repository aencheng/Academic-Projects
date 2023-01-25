import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

// Class used to set up the account and log in
public class ProjectDriver {
	private Data data;
	private User user;
	private Admin admin;
	
	public ProjectDriver(Data data) {
		this.data = data;
		this.admin = new Admin();
	}
	
	
	//Method to log in using a unique username and password
	public void login(String UserName, String Password) throws FileNotFoundException {
		String userName = UserName;
		String password = Password;
		String choice = "";
		ArrayList<User> users = this.data.getusers();
		boolean foundUser = false;
		while(!foundUser)
		{
			for(int i = 0; i < users.size(); i++)
			{
				User check = users.get(i);
				if(check.getUserName().equals(userName) && check.getPassword().equals(password))
				{
					this.setUser(check);
					foundUser = true;
				}
			}
			if(!foundUser)
			{
				System.out.println("Sorry your username and password were not found. Please try again.");
				System.out.print("Username: ");
				Scanner menu = new Scanner(System.in);
				userName = menu.next();
				System.out.print("Password: ");
				password = menu.next();
			}
		}
		if(this.user.getUserName().equals("root") && this.user.getPassword().equals("1234"))
		{
			
			this.admin.SignedIn(this.data.getLibrary());
			this.user.history = this.admin.getAdminMovie();
			choice = "LogOut";
			
		}
		
		while(!choice.equals("LogOut"))
		{
			
			System.out.println("User Menu:\n_____________________");
			
			System.out.println("What would you like to do?\n1. Purchase Ticket\n2. View History\n3. View Tickets\n4. Rate Movie\n5. Delete Account\n6. LogOut");
			Scanner input = new Scanner(System.in);
			choice = input.nextLine();
			if(choice.equals("Purchase Ticket") || choice.equals("1"))
			{
				String dashline = new String();
				dashline = "============================================";

				System.out.printf("%-20.20s %10.10s %5.5s %6.6s %11.11s\n", "Title", "Genre", "Time", "Rating", "Cost/Ticket");
				System.out.printf("%-20.20s %10.10s %5.5s %6.6s %11.11s\n", dashline, dashline, dashline, dashline, dashline);

				//System.out.printf("%-10.10s %5.5s %5.5s %5.5s %5.5s\s", dashline, dashline, dashline, dashline, dashline);

				// this will create a menu that looks like:
				// ==========     =====     =====     ======     ================
				// Movie name     Genre     time      rating     cost per ticket
				// Movie name     Genre     time      rating     cost per ticket
				// Movie name     Genre     time      rating     cost per ticket
				//
				// the user will now be able to select which movie they want to buy.
				MovieLibrary library = this.data.getlibrary();
				ArrayList<Movie> Library = library.getlibrary();
				for(int j = 0; j < Library.size(); j++)
				{
					Movie MovieObj = Library.get(j);

					System.out.printf("%d. %-20.20s %10.10s %5.5s %6.6s %11.11s\n", j+1, MovieObj.getTitle(), MovieObj.getGenre(), MovieObj.getTime(), String.valueOf(MovieObj.getAvgRating()), String.valueOf(MovieObj.getPrice()));

					//System.out.printf("%-10s %15s %5s %5s %5s\n", MovieObj.getTitle(), MovieObj.getGenre(), MovieObj.getTime(), MovieObj.getAvgRating(), MovieObj.getPrice());
				}
				System.out.println();
				System.out.print("Which movie would you like to purchase tickets for? (Please enter the full title)\n");
				choice = input.nextLine();
				for(Movie m : Library) {
					if(choice.equals(m.getTitle())) {
						this.user.purchaseTicket(m);
						this.user.addHistory(m);
					}
				}
				
				
				
			}
			else if(choice.equals("View History") || choice.equals("2"))
			{
				String dashline = new String();
				dashline = "============================================";
				System.out.printf("%-20.20s %10.10s %5.5s %6.6s %11.11s\n", "Title", "Genre", "Time", "Rating", "Cost/Ticket");
				System.out.printf("%-20.20s %10.10s %5.5s %6.6s %11.11s\n", dashline, dashline, dashline, dashline, dashline);
				User UserObj = this.user;
				ArrayList<Movie> history = this.user.getHistory();
				for(int j = 0; j < history.size(); j++)
				{
					Movie MovieObj = history.get(j);
					System.out.printf("%-20.20s %10.10s %5.5s %6.6s %11.11s\n", MovieObj.getTitle(), MovieObj.getGenre(), MovieObj.getTime(), String.valueOf(MovieObj.getAvgRating()), String.valueOf(MovieObj.getPrice()));
				}
			}
			
			
			else if(choice.equals("View Tickets") || choice.equals("3")) {
				this.user.viewTickets();
			}
			
			
			else if(choice.equals("Rate Movie") || choice.equals("4")) 
			{
				
				ArrayList<Movie> history = this.user.getHistory();
				System.out.println("Which movie would you like to rate?");
				for(int i = 0; i < this.user.getHistory().size(); i++) 
				{
					
					Movie MovieObj = history.get(i);
					System.out.printf("%d. %s\n", i+1, MovieObj.getTitle());	
				}
				String choice2 = input.nextLine();
				Movie movie = history.get(Integer.valueOf(choice2)-1);
				System.out.printf("What would you rate %s\n", movie.getTitle());
				double rating = input.nextInt();
				
				for(Movie m : data.getlibrary().getMovies()) 
				{
					if(m.getTitle() == movie.getTitle())
					{
						this.user.rateMovie(m, rating);
					}
				}
				
			}
			
			else if(choice.equals("Delete Account") || choice.equals("5"))
			{
				this.data.DeleteUser(this.user);
				System.out.println("Done, " + this.user.getUserName() + "'s account has been deleted. have a good day");
				break;
			}
			
			
			else if(choice.equals("LogOut") || choice.equals("6"))
			{
				break;
			}
			
			else
			{
				System.out.println("Invalid option, please try again: \n");
				System.out.println("What would you like to do?\n1. Purchase Ticket\n2. View History\n3. Rate Movie\n4. LogOut");
				input = new Scanner(System.in);
				choice = input.nextLine();
			}
		}
		//find user data and load it
		//User currentUser = wherever it is
	}
	// Method to create an account for the system
	public void createAccount() throws FileNotFoundException {
		boolean confirmed = false;
		Scanner sc = new Scanner(System.in);
		ArrayList<User> userArray = this.data.getusers();
		System.out.print("Choose a username: "); //make sure username is not taken already
		String newUsername = sc.next();
		while(!confirmed)
		{
			
			for(int i= 0; i < userArray.size(); i++)
			{
				User user = userArray.get(i);
				if(!user.getUserName().equals(newUsername))
				{
					confirmed = true;
				}
				else
				{
					System.out.println("Sorry, this username is already taken. Please try again: ");
					System.out.print("Choose a username: "); //make sure username is not taken already
					newUsername = sc.next();
					confirmed = false;
				}
						
			}
		}
		
		confirmed = false;
		
		while(!confirmed) {
			System.out.print("Choose a password: ");
			String newPassword = sc.next();
			System.out.print("Confirm password: ");
			String confirmPassword = sc.next();
			
			if(newPassword.equals(confirmPassword)) {
				//data.addUser(newUsername, newPassword); 

				System.out.println("--Account Created--\n");
				
				this.data.addUser(newUsername, confirmPassword);
				this.login(newUsername,newPassword);
				confirmed = true;
			}
			else {
				System.out.println("Passwords do not match!");
			}
		}
		//sc.close();
	}
	
	// Main method for the class
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stuff
		Data data = new Data();  
		
		
		ProjectDriver driver = new ProjectDriver(data);
		String Menuechoice = "";

		while(!Menuechoice.equals("Exit"))
		{
			System.out.println("Menu:\n_____________________\n1. Returning User\n2. Create Account\n3. Exit\n");
			Scanner menu = new Scanner(System.in);
			Menuechoice = menu.nextLine();
			if(Menuechoice.equals("Returning User") || Menuechoice.equals("1"))
			{
				System.out.print("Username: ");
				String username = menu.next();
				System.out.print("Password: ");
				String password = menu.next();
				MovieLibrary obj = new MovieLibrary();
				System.out.println(obj.getlibrary().size());
				driver.login(username, password);
				
			}
			else if(Menuechoice.equals("Create Accound") || Menuechoice.equals("2"))
			{
				driver.createAccount();
			}
			else if(Menuechoice.equals("3"))
			{
				
				break;
			}
			else
			{
				System.out.println("Invalid choice, try again.");
			}

			
		}
		System.out.println("Good bye :) have a great day!");
		data.writeData();
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	

}
