package BLL;

import BE.Genre;
import BE.Movie;
import BLL.Utils.VlcHandler;
import DAL.DBManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MovieManager {

    private DBManager dbMgr = new DBManager();
    private static MovieManager instance = null;

    private static VlcHandler vlcHandler = new VlcHandler();

    /**
     * Get-method for retrieving the MusicManager instance.
     *
     * @return The instance of the MusicManager
     * @throws NullPointerException Thrown if the MusicManager is not
     *                              initialized via the <i>init()</i> method.
     */
    public static MovieManager getInstance() {
        if (instance == null) {
            return new MovieManager();
        } else {
            return instance;
        }
    }

    public void playMovie(Movie movie) throws Exception {
        movie.setLastView(new Timestamp(System.currentTimeMillis()));
        dbMgr.updateMovie(movie);
        vlcHandler.OpenVlc(movie);
    }

    public Movie add(Movie movie) throws Exception {
        return dbMgr.addMovie(movie);
    }

    public void delete(Movie movie) throws Exception {
        dbMgr.deleteMovieByID(movie.getId());
    }

    public void update(Movie movie) throws Exception {
        dbMgr.updateMovie(movie);
    }

    public Movie getById(int movieId) throws Exception {
        return dbMgr.getMovieByID(movieId);
    }

    public Movie getByName(String name) throws Exception {
        return dbMgr.getMovieByName(name);
    }

    public List<Movie> getAllMovies() throws Exception {
        return dbMgr.getAllMovies();
    }

    
}
