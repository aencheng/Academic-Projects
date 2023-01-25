import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Data {

	private ArrayList<User> users = new ArrayList<User>();
	private Map<User, String[]> loginCredentials = new HashMap<User, String[]>();
	private MovieLibrary library = new MovieLibrary();
	private ArrayList<Movie> masterArr = new ArrayList<Movie>();
	private Map<User, ArrayList<Ticket>> tickets;
	private User UserObj;
	public File inputFile = new File("data.txt");
	public Scanner in;
	public PrintWriter out;
	private Admin AdminObj;
	
	/**
	 * Constructor for constructing the Data, which holds all previous submitted information
	 * @throws FileNotFoundException 
	 */
	Data() throws FileNotFoundException
	{
		this.in = new Scanner(this.inputFile);
		this.pullData();
		
	}

	/**
	 * Method to Write Data and add it into current Information of Data
	 * @throws FileNotFoundException 
	 */
	public void writeData() throws FileNotFoundException
	{
		this.out = new PrintWriter("data.txt");
		//*
		//this.out.print("New file");
		for(int j = 0; j <= this.users.size() - 1; j++)
		{
			//System.out.println(j); // print out where the counter is

			//System.out.println("user array is " + (this.users.size() - 1)); // print out how big the array is
			User userObj = this.users.get(j); // get the user object
			this.out.println(userObj.getUserName() + "-" + userObj.getPassword()); // print out the user object username and password to the file associated with this.out
			//System.out.println("I am adding " + userObj.getUserName() + "-" + userObj.getPassword()); // print user object information to keep track which user we are adding
			String MovieLine = new String();
			if(userObj.history.size() != 0)
			{
				for(int i = 0; i < userObj.history.size(); i++)
				{
					Movie MovieObj = userObj.history.get(i);  // get a movie object
					if(i != userObj.history.size() - 1)
					{
						//System.out.println("I am adding the Movie " + MovieObj.getTitle() ); // check which movie object we are adding
						MovieLine += MovieObj.getTitle() + "-" + MovieObj.getGenre() + "-" + MovieObj.getTime() + "-" +  MovieObj.getLocation() + "-" + MovieObj.getReleaseDate() + "-" + MovieObj.getRunning() + "-" + MovieObj.getAvgRating() + "-" + MovieObj.getNumberOfRatings() + "-" + MovieObj.getPrice() + "-,-";
						// print the movie object information
					}
					else
					{
						//System.out.println("I am adding the Movie " + MovieObj.getTitle() );
						MovieLine += MovieObj.getTitle() + "-" + MovieObj.getGenre() + "-" + MovieObj.getTime() + "-" +  MovieObj.getLocation() + "-" + MovieObj.getReleaseDate() + "-" + MovieObj.getRunning() + "-" + MovieObj.getAvgRating() + "-" + MovieObj.getNumberOfRatings() + "-" + MovieObj.getPrice() + "- -";
						// print the movie object information with a - - at the end to mark the end of the movie list
					}
				}
			}
			else
			{
				MovieLine = "";
			}
			//System.out.println(MovieLine);
			this.out.println(MovieLine);
			
		}	
		out.close();
	}

	/**
	 * Method to pull Data that has saved all previous records and histories so it can remember it to be used for next use.
	 * @throws FileNotFoundException 
	 */

	public void pullData() throws FileNotFoundException 
	{
		int LineCnt = 1;
		this.inputFile = new File("data.txt");

		//this.in = new Scanner(this.inputFile); //what does this.in do

		this.in = new Scanner(this.inputFile);

		while(this.in.hasNextLine())
		{
			int count = 0;
			String line = in.nextLine();

			String[] Line = line.split("-");

			if((LineCnt % 2) != 0)
			{
				this.UserObj = new User(Line[0], Line[1]);
				this.loginCredentials.put(this.UserObj, Line);
				this.users.add(this.UserObj);
				
			}
			else
			{
				
				boolean nextMovie = true;
				
				if(Line.length == 1) 
				{
					nextMovie = false;
				}
				while(nextMovie) 
				{
					for(int i = 0; i < Line.length; i++) 
					{
						String title = Line[count];
						count++;
						String Genre = Line[count];
						count++;
						String time = Line[count];
						count++;
						String Location = Line[count];
						count++;
						String ReleaseDate = Line[count];
						count++;
						String Running = Line[count];
						count++;
						String advRating = Line[count];
						count++;
						String numRating = Line[count];
						count++;
						String price = Line[count];
						count++;
						Movie MovieObj = new Movie(title, Genre, time, Location, ReleaseDate, Running, Double.valueOf(advRating), Double.valueOf(numRating), Double.valueOf(price));
						
						if(this.UserObj.getUserName().equals("root") && this.UserObj.getPassword().equals("1234"))
						{
							
							//System.out.println(this.getLibrary().getMovies().size());
							//ArrayList<Movie> search = this.getLibrary().getlibrary();
							this.library.addMovie(new Movie(title, Genre, time, Location, ReleaseDate, Running, Double.valueOf(advRating), Double.valueOf(numRating), Double.valueOf(price)));
							
							
							this.UserObj.addHistory(MovieObj);
							
						}
						
						else
						{
							ArrayList<Movie> search = this.getLibrary().getlibrary();
							for(int j = 0; j < search.size(); j++)
							{
								Movie addMovie = search.get(j);
								if(MovieObj.getTitle().equals(addMovie.getTitle()))
								{
									this.UserObj.addHistory(search.get(j));

								}
							}
							
						}
						//this.library.addMovie(new Movie(title, Genre, cast, time, Location, Double.valueOf(advRating), Double.valueOf(numRating), Double.valueOf(price)));

						if(Line[count].equals(","))
						{
							count++;
							nextMovie=true;
						}
						
						if(Line[count].equals(" ")) 
						{
							nextMovie=false;
							break;
						}
					}
				}
			}
			LineCnt++;
		}
		in.close();
		
	}
	
	public void addUser(String username, String Password)
	{
		User userObj = new User(username, Password);
		this.users.add(userObj);
	}
	
	public  Map<User, String[]> getloginCredentials()
	{
		return this.loginCredentials;
	}
	
	public ArrayList<User> getusers()
	{
		return this.users;
	}
	
	public MovieLibrary getlibrary()
	{
		return this.library;
	}
	
	public void DeleteUser(User u)
	{
		for(int i = 0; i < this.users.size(); i++)
		{
			User user = this.users.get(i);
			if(user.getUserName().equals(u.getUserName()) && user.getPassword().equals(u.getPassword()))
			{
				this.users.remove(u);
			}
		}
	}

	public MovieLibrary getLibrary() {
		return library;
	}

	public void setLibrary(MovieLibrary library) {
		this.library = library;
	}

	public ArrayList<Movie> getMasterArr() {
		return masterArr;
	}

	public void setMasterArr(ArrayList<Movie> masterArr) {
		this.masterArr = masterArr;
	}
	
	
	
}
