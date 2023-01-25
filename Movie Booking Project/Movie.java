public class Movie extends MovieLibrary{
	private String title;
	private String genre;
	
	private String time;
	private String location;
	private String ReleaseDate;
	private String Running;
	private double avgRating;
	private double numberOfRatings;
	private double price;
	

	/**
	 * Constructor for constructing the Movie
	 */
	public Movie(String title, String genre, String time, String location, String ReleaseDate, String Running, double avgRating,double numberOfRatings, double price) {
		super();
		this.title = title;
		this.genre = genre;
		
		this.time = time;
		this.location = location;
		this.ReleaseDate = ReleaseDate;
		this.Running = Running;
		this.avgRating = avgRating;
		this.numberOfRatings = numberOfRatings;
		//super.addMovie(this);
		this.setPrice(price);
		
	}

	/**
	 * Getter to get Movie Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter to set Movie Title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter to get Movie Genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Setter to set Movie Genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * Getter to get Movie Cast
	 */


	/**
	 * Getter to get Movie Time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Setter to set Movie Time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Getter to get Movie Location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Setter to set Movie Location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Getter to get Movie Average Rating
	 */
	public double getAvgRating() {
		return this.avgRating;
	}

	/**
	 * Setter to set Movie Average Rating
	 */
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getNumberOfRatings() {
		return this.numberOfRatings;
	}

	public void setNumberOfRatings(double numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}
	
	public void addRating(double rating) {
		this.numberOfRatings += 1;
		this.setAvgRating(this.getAvgRating() + (rating - this.getAvgRating())/this.numberOfRatings);
		
	}

	public String getReleaseDate() {
		return ReleaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		ReleaseDate = releaseDate;
	}

	public String getRunning() {
		return Running;
	}

	public void setRunning(String running) {
		Running = running;
	}
}
