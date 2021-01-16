package BLL;

import BE.Genre;
import BE.Movie;
import DAL.DBManager;
import java.util.List;

/**
 * The type Genre manager.
 */
public class GenreManager  {

    private DBManager dbMgr = new DBManager();
    private static GenreManager instance = null;

    /**
     * Get-method for retrieving the GenreManager instance.
     *
     * @return The instance of the GenreManager
     * @throws NullPointerException Thrown if the GenreManager is not         initialized via the <i>init()</i> method.
     */
    public static GenreManager getInstance(){
        if( instance == null ){
            return new GenreManager();
        }else{
            return instance;
        }
    }

    /**
     * Adds genre.
     *
     * @param genre the genre
     * @return the genre
     * @throws Exception the exception
     */
    public Genre add(String genre) throws Exception
    {
        return dbMgr.addGenre(new Genre(genre));
    }

    /**
     * Adds genre.
     *
     * @param genre the genre
     * @return the genre
     * @throws Exception the exception
     */
    public Genre add(Genre genre) throws Exception
    {
        return dbMgr.addGenre(genre);
    }

    /**
     * Deletes genre.
     *
     * @param genre the genre
     * @throws Exception the exception
     */
    public void delete(Genre genre) throws Exception
    {
        dbMgr.deleteGenreByID(genre.getId());
    }

    /**
     * Updates genre.
     *
     * @param genre the genre
     * @throws Exception the exception
     */
    public void update(Genre genre) throws Exception
    {
        dbMgr.updateGenre(genre);
    }

    /**
     * Gets by id.
     *
     * @param genreId the genre id
     * @return the by id
     * @throws Exception the exception
     */
    public Genre getById(int genreId) throws Exception
    {
        return dbMgr.getGenreByID(genreId);
    }

    /**
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     * @throws Exception the exception
     */
    public Genre getByName(String name) throws Exception
    {
        return dbMgr.getGenreByName(name);
    }

    /**
     * Add genre to movie.
     *
     * @param genre the genre
     * @param movie the movie
     * @throws Exception the exception
     */
    public void addGenreToMovie(Genre genre, Movie movie) throws Exception
    {
        dbMgr.addGenreToMovie(movie, genre);
    }

    /**
     * Gets all genres.
     *
     * @return all genres
     * @throws Exception the exception
     */
    public List<Genre> getAllGenres() throws Exception {
        return dbMgr.getAllGenres();
    }
}
