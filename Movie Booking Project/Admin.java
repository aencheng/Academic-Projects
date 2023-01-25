import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User{
	//this class will hold all of the commands that the admin can access. 
	
	private final static String USERNAME = "root";
	private final static String PASSWORD = "password";
	private ArrayList<Movie> AdminMovie = new ArrayList<Movie>();
	//private User user;
	private Movie MovieObj;
	
	
	
	
	
	Admin() { //constructor
		super(USERNAME, PASSWORD);
		
		//this.user = new User(USERNAME, PASSWORD);
	}

	
	public void viewMovies()
	{ // will return a string of the movies
		
		
		System.out.println("I am in View Movies");
		
		String dashline = new String();
		dashline = "============================================";
		System.out.printf("    %-20.20s %10.10s %4.4s %6.6s %10.10s\n", "Title", "Genre", "Time", "Rating", "Cost/Ticket");
		System.out.printf("    %-20.20s %10.10s %4.4s %6.6s %10.10s\n", dashline, dashline, dashline, dashline, dashline);
		for(int i = 0; i < this.AdminMovie.size(); i ++)
		{
			MovieObj = this.AdminMovie.get(i);
			System.out.printf("%2d. %-20.20s %10.10s %4.4s %6.6s %10.10s\n", i+1, MovieObj.getTitle(), MovieObj.getGenre(), MovieObj.getTime(), String.valueOf(MovieObj.getAvgRating()), String.valueOf(MovieObj.getPrice()));
			
		}
	}
	
	public void postMovie()
	{ 
		System.out.println("What is the title of the movie you would like to add: ");
		Scanner input = new Scanner(System.in);
		String title = input.nextLine();
		System.out.println("What is the Genre of " + title + ": ");
		String genre = input.nextLine();
		System.out.println("What time is " + title + " showing ");
		String time = input.nextLine();
		System.out.println("What room is this movie showing in?");
		String location = input.nextLine();
		System.out.println("What is the release date?");
		String ReleaseDate = input.nextLine();
		System.out.println("Is this movie running?");
		String Running = input.nextLine();
		System.out.println("What is the Adverage rating for " + title + ": ");
		Double AvdRate = input.nextDouble();
		System.out.println("What is the Price per ticket?");
		Double price = input.nextDouble();
		
		Movie MovieObj = new Movie(title, genre, time, location, ReleaseDate, Running, AvdRate, 1, price);
		
		
		this.AdminMovie.add(MovieObj);
		
		
	}
	
	public void editMove()
	{ 
		this.viewMovies();
		// this will edit an exidting movie for things such as show time and cost ect.
		System.out.println("Enter the title of the movie would you like to edit? ");
		
		Scanner input = new Scanner(System.in);
		String choice = input.nextLine();
		
		
		for(int i = 0; i < this.AdminMovie.size(); i++)
		{
			Movie MovieObj = this.AdminMovie.get(i);
			
			if(MovieObj.getTitle().equals(choice))
			{
				this.MovieObj = MovieObj;
			}
			else
			{
				continue;
			}
		}
		System.out.println("What would you like to change about " + choice + ".");
		System.out.println("1. The Title\n2. The Genre\n3 The Time\n4. The Location\n5. The release date\n6. The Price per Ticket");
		input = new Scanner(System.in);
		choice = input.nextLine();
		
		if(choice.equals("Title") || choice.equals("1"))
		{
			System.out.println("What would you like to set as the new title?");
			String title = input.nextLine();
			this.MovieObj.setTitle(title);
			System.out.println("Done. The title is now " + this.MovieObj.getTitle());
		}
		else if(choice.equals("Genre") || choice.equals("2"))
		{
			System.out.println("What would you like to set as the new genre?");
			String genre = input.nextLine();
			this.MovieObj.setGenre(genre);
			System.out.println("Done. The Genre is now " + this.MovieObj.getGenre());
		}
		else if(choice.equals("Time") || choice.equals("3"))
		{
			System.out.println("What would you like to set as the new Time?");
			String Time = input.nextLine();
			this.MovieObj.setGenre(Time);
			System.out.println("Done. The Time is now " + this.MovieObj.getTime());
		}
		else if(choice.equals("Location") || choice.equals("4"))
		{
			System.out.println("What would you like to set as the new Location?");
			String Location = input.nextLine();
			this.MovieObj.setGenre(Location);
			System.out.println("Done. The Location is now " + this.MovieObj.getLocation());
		}
		else if(choice.equals("release date") || choice.equals("5"))
		{
			System.out.println("What would you like to set as the new release date?");
			String date = input.nextLine();
			this.MovieObj.setGenre(date);
			//System.out.println("Done. The release date is now " + this.MovieObj.getDate());
		}
		else if(choice.equals("Price per Ticket") || choice.equals("6"))
		{
			System.out.println("What would you like to set as the new Price per Ticket?");
			String Ticket = input.nextLine();
			this.MovieObj.setGenre(Ticket);
			System.out.println("Done. The Price per Ticket is now " + this.MovieObj.getPrice());
		}
		
		
	}
	
	public void deleteMovie()
	{ // this will sign the admin out 
		this.viewMovies();
		
		System.out.println("Which movie would you like to delete?");
		Scanner input = new Scanner(System.in);
		String choice = input.nextLine();
		
		for(int i = 0; i < this.AdminMovie.size(); i++)
		{
			Movie MovieObj = this.AdminMovie.get(i);
			if(choice.equals(MovieObj.getTitle()))
			{
				this.AdminMovie.remove(i);
				System.out.println("Done " + MovieObj.getTitle() + " has been removed");
			}	
		}
	}
	
	public void SignedIn(MovieLibrary Movies)
	{
		this.AdminMovie = Movies.getMovies();
		String choice = "";
		while(!choice.equals("Sign Out"))
		{
			System.out.println("Welcome Admin! What would you like to do?\n1. View Movies\n2. Post a new Movie\n3. Edit an existing movie\n4. Delete Movie\n5. Sign Out\n");
			Scanner input = new Scanner(System.in);
			choice = input.nextLine();
			if(choice.equals("View Movies") || choice.equals("1"))
			{
				System.out.println("I am going to View Movies");
				this.viewMovies();
			}
			if(choice.equals("Post a new Movie") || choice.equals("2"))
			{
				this.postMovie();
			}
			if(choice.equals("Edit an existing movie") || choice.equals("3"))
			{
				this.editMove();
			}
			if(choice.equals("Delete Movie") || choice.equals("4"))
			{
				this.deleteMovie();
			}

			if(choice.equals("Sign Out") || choice.equals("5")) {

				break;
			}
			
		}
		
	}


	public ArrayList<Movie> getAdminMovie() {
		return AdminMovie;
	}


	public void setAdminMovie(ArrayList<Movie> adminMovie) {
		AdminMovie = adminMovie;
	}
	
	public void AddAdminMovie(Movie MovieObj) {
		this.AdminMovie.add(MovieObj);
	}
	

}
