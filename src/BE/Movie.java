package BE;

import javax.lang.model.type.ArrayType;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Movie extends BaseEntity{
    private String title;
    private float myRating;
    private float imdbRating;
    private ArrayList<Genre> genres;
    private String fileLink;
    private java.sql.Timestamp lastView;

    public Movie(int id, Movie movie)
    {
        this( id,
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
     * @param title Title of the movie.
     * @param category Category of the movie.
     * @param filename Filename of the movie.
     */

    public Movie(String title, float myRating, float imdbRating,String fileLink, java.sql.Timestamp lastView ,ArrayList<Genre> genres) {
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
     * @param id ID of the movie in the database.
     * @param title Title of the movie.
     * @param category Category of the movie.
     * @param filepath Filename of the movie.
     */
    public Movie(int id, String title, float myRating, float imdbRating,String fileLink, java.sql.Timestamp lastView,ArrayList<Genre> genres ) {
        this.setId(id);
        this.setTitle(title);
        this.setMyRating(myRating);
        this.setImdbRating(imdbRating);
        this.setGenres(genres);
        this.setFileLink(fileLink);
        this.setLastView(lastView);
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

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
    public ArrayList<Genre> getGenres() {
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
     * New toString method for Track, used for formatting the view in the ListView in GUI
     * @return "{Track Title} - {Track Artist}
     */
    @Override
    public String toString() {
        return this.getTitle();
    }

}
