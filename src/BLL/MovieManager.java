package BLL;

import BE.Genre;
import BE.Movie;
import DAL.DBManager;

import java.util.ArrayList;

public class MovieManager {

    private DBManager dbMgr = new DBManager();
    private static MovieManager instance = null;

    /**
     * Get-method for retrieving the MusicManager instance.
     *
     * @throws NullPointerException Thrown if the MusicManager is not
     *         initialized via the <i>init()</i> method.
     * @return The instance of the MusicManager
     */
    public static MovieManager getInstance(){
        if( instance == null ){
            return new MovieManager();
        }else{
            return instance;
        }
    }
    public Movie add(Movie movie) throws Exception
    {
        return dbMgr.addMovie(movie);
    }

    public void delete(Movie movie) throws Exception {
        dbMgr.deleteGenreByID(movie.getId());
    }
    public void update(Movie movie) throws Exception {
        dbMgr.updateMovie(movie);
    }
    public Movie getById(int movieId) throws Exception
    {
        return dbMgr.getMovieByID(movieId);
    }
    public Movie getByName(String name) throws Exception
    {
        return dbMgr.getMovieByName(name);
    }

    public ArrayList<Movie> getAllMovies()throws Exception {
        return dbMgr.getAllMovies();
    }
}
