package BE;

import javax.lang.model.type.ArrayType;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Movie extends BaseEntity {
    private String title;
    private float myRating;
    private float imdbRating;
    private List<Genre> genres;
    private String fileLink;
    private java.sql.Timestamp lastView;

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
     * @param title    Title of the movie.
     * @param category Category of the movie.
     * @param filename Filename of the movie.
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
     * @param id       ID of the movie in the database.
     * @param title    Title of the movie.
     * @param genres Genres of the movie.
     * @param fileLink Filepath of the movie.
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

    public Movie() {
        this.setId(-1);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setMyRating(float myRating) {
        this.myRating = myRating;
    }

    public float getMyRating() {
        return myRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public void setLastView(java.sql.Timestamp lastView) {
        this.lastView = lastView;
    }

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

    public String getMyRatingString() {
        return getRatingString(myRating);
    }

    public String getImdbRatingString() {
        return getRatingString(imdbRating);
    }
}
