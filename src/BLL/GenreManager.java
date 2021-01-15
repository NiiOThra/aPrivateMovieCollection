package BLL;

import BE.Genre;
import BE.Movie;
import DAL.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreManager  {

    private DBManager dbMgr = new DBManager();
    private static GenreManager instance = null;

    /**
     * Get-method for retrieving the MusicManager instance.
     *
     * @throws NullPointerException Thrown if the MusicManager is not
     *         initialized via the <i>init()</i> method.
     * @return The instance of the MusicManager
     */
    public static GenreManager getInstance(){
        if( instance == null ){
            return new GenreManager();
        }else{
            return instance;
        }
    }
    public Genre add(String genre) throws Exception
    {
        return dbMgr.addGenre(new Genre(genre));
    }
    public Genre add(Genre genre) throws Exception
    {
        return dbMgr.addGenre(genre);
    }
    public void delete(Genre genre) throws Exception
    {
        dbMgr.deleteGenreByID(genre.getId());
    }
    public void update(Genre genre) throws Exception
    {
        dbMgr.updateGenre(genre);
    }
    public Genre getById(int genreId) throws Exception
    {
        return dbMgr.getGenreByID(genreId);
    }
    public Genre getByName(String name) throws Exception
    {
        return dbMgr.getGenreByName(name);
    }
    public void addGenreToMovie(Genre genre, Movie movie) throws Exception
    {
        dbMgr.addGenreToMovie(movie, genre);
    }

    public List<Genre> getAllGenres() throws Exception {
        return dbMgr.getAllGenres();
    }
}
