import java.util.ArrayList;
/**
 * This class is used to store all movies in a single place
 * @author Nicholas Stiner, Andrew Cheng, Silas DeLine, Griffin Wall
 *
 */
public class MovieLibrary {
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	
	/**
	 * Adds a movie to the array of movies
	 * @param m the movie to be added to the array
	 */
	public MovieLibrary()
	{
		
	}
	public void addMovie(Movie m) {
		//System.out.println("I am adding movie " + m.getTitle());
		this.movies.add(m);
	}
	
	public ArrayList<Movie> getlibrary()
	{
		return this.getMovies();
	}
	public ArrayList<Movie> getMovies() {
		return movies;
	}
	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}
	public void DeleteMovie(Movie m)
	{
		
	}
	
	public void ViewMovies()
	{
		String dashline = new String();
		dashline = "============================================";
		System.out.printf("%-20.20s %10.10s %4.4s %6.6s %10.10s\n", "Title", "Genre", "Time", "Rating", "Cost/Ticket");
		System.out.printf("%-20.20s %10.10s %4.4s %6.6s %10.10s\n", dashline, dashline, dashline, dashline, dashline);
		//User UserObj = this.user;
		//ArrayList<Movie> history = this.user.getHistory();
		for(int j = 0; j < this.movies.size(); j++)
		{
			Movie MovieObj = this.movies.get(j);
			System.out.printf("%-20.20s %10.10s %5.5s %6.6s %11.11s\n", MovieObj.getTitle(), MovieObj.getGenre(), MovieObj.getTime(), String.valueOf(MovieObj.getAvgRating()), String.valueOf(MovieObj.getPrice()));
		}
	}
	
	
	
}
