package BE;

import java.util.List;

/**
 * The type Movie.
 */
public class Movie extends BaseEntity {
    private String title;
    private float myRating;
    private float imdbRating;
    private List<Genre> genres;
    private String fileLink;
    private java.sql.Timestamp lastView;

    /**
     * Instantiates a new Movie.
     *
     * @param id    the id
     * @param movie the movie
     */
    public Movie(int id, Movie movie) {
        this(id,
                movie.getTitle(),
                movie.getMyRating(),
                movie.getImdbRating(),
                movie.getFileLink(),
                movie.getLastView(),
                movie.getGenres());
    }

    /**
     * Constructor for the Movie class. This constructor is used when adding a
     * new movie to the database.
     *
     * @param title      Title of the movie.
     * @param myRating   the my rating
     * @param imdbRating the imdb rating
     * @param fileLink   Filepath of the movie.
     * @param lastView   the last view
     * @param genres     Genres of the movie.
     */
    public Movie(String title, float myRating, float imdbRating, String fileLink, java.sql.Timestamp lastView, List<Genre> genres) {
        this.setTitle(title);
        this.setMyRating(myRating);
        this.setImdbRating(imdbRating);
        this.setGenres(genres);
        this.setFileLink(fileLink);
        this.setLastView(lastView);
    }

    /**
     * Constructor for the Movie class. This constructor is never really used,
     * since the other two cover most situations.
     *
     * @param id         ID of the movie in the database.
     * @param title      Title of the movie.
     * @param myRating   the my rating
     * @param imdbRating the imdb rating
     * @param fileLink   Filepath of the movie.
     * @param lastView   the last view
     * @param genres     Genres of the movie.
     */
    public Movie(int id, String title, float myRating, float imdbRating, String fileLink, java.sql.Timestamp lastView, List<Genre> genres) {
        this.setId(id);
        this.setTitle(title);
        this.setMyRating(myRating);
        this.setImdbRating(imdbRating);
        this.setGenres(genres);
        this.setFileLink(fileLink);
        this.setLastView(lastView);
    }

    /**
     * Instantiates a new Movie.
     */
    public Movie() {
        this.setId(-1);
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets my rating.
     *
     * @param myRating the my rating
     */
    public void setMyRating(float myRating) {
        this.myRating = myRating;
    }

    /**
     * Gets my rating.
     *
     * @return the my rating
     */
    public float getMyRating() {
        return myRating;
    }

    /**
     * Sets imdb rating.
     *
     * @param imdbRating the imdb rating
     */
    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    /**
     * Gets imdb rating.
     *
     * @return the imdb rating
     */
    public float getImdbRating() {
        return imdbRating;
    }

    /**
     * Sets genres.
     *
     * @param genres the genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     * Gets genres.
     *
     * @return the genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Gets filepath.
     *
     * @return the filepath
     */
    public String getFileLink() {
        return fileLink;
    }

    /**
     * Sets filepath.
     *
     * @param fileLink the filepath
     */
    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    /**
     * Sets last view.
     *
     * @param lastView the last view
     */
    public void setLastView(java.sql.Timestamp lastView) {
        this.lastView = lastView;
    }

    /**
     * Gets last view.
     *
     * @return the last view
     */
    public java.sql.Timestamp getLastView() {
        return lastView;
    }


    /**
     * New toString method for Movie, used for formatting the view in the ListView in GUI
     *
     * @return "{Movie Title}"
     */
    @Override
    public String toString() {
        return this.getTitle() ;
    }

    /**
     * Gets genres string.
     *
     * @return the genres string
     */
    public String getGenresString() {
        String genresString = "";
        for (Genre genre : genres) {
            genresString += genre.getName() + ", ";
        }
        if (genresString.isEmpty()) {
            return genresString;
        } else {
            return genresString.substring(0, genresString.lastIndexOf(','));
        }
    }

    private String getRatingString(float rating) {
        return String.valueOf(rating) + "/10";
    }

    /**
     * Gets my rating string.
     *
     * @return the my rating string
     */
    public String getMyRatingString() {
        return getRatingString(myRating);
    }

    /**
     * Gets imdb rating string.
     *
     * @return the imdb rating string
     */
    public String getImdbRatingString() {
        return getRatingString(imdbRating);
    }
}
