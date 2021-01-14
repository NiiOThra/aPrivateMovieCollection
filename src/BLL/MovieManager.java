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
        dbMgr.deleteGenresOnMovie(movie.getId());

        for (Genre g : movie.getGenres()) {
            dbMgr.addGenreToMovie(movie, g);
        }

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

    public List<Movie> getExpiredMovies() throws Exception {
        List<Movie> expiredMovies = new ArrayList<>();
        var allMovies = dbMgr.getAllMovies();

        for (Movie m : allMovies) {
            // Check if older than 2 years
            var timeDiff = compareTwoTimeStamps(new Timestamp(System.currentTimeMillis()), m.getLastView());
            if (timeDiff > 365 * 2) // if time difference is over 365 days times 2.
            {
                expiredMovies.add(m);
            } else if (m.getMyRating() < 6) {
                expiredMovies.add(m);
            }
        }

        return expiredMovies;
    }

    public static long compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime) {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();

        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }
}
