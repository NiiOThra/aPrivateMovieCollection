package DAL;

import BE.Genre;
import BE.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private SQLServerDataSource dataSource;
    private static DBManager instance = null;

    public DBManager()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("PrivateMovieCollection");
        dataSource.setUser("CSe20A_21");
        dataSource.setPassword("CSe20A_21");
        dataSource.setPortNumber(1433);
    }
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    /**
     * Returns all movies in a list
     *
     * @return a list of all movies
     * @throws SQLException
     */
    public List<Movie> getAllMovies() throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery( "SELECT * FROM Movie" );

            List<Movie> movies = new ArrayList<>();
            while( rs.next() ){
                movies.add( getOneMovie( rs ) );
            }
            return movies;
        }
    }
    /**
     * @param rs the resultset from a SQL query
     * @return a movie object
     * @throws SQLException
     */
    private Movie getOneMovie( ResultSet rs ) throws SQLException{
        int id = rs.getInt( 1 );
        String title = rs.getString( 2 );
        float imdbRating = rs.getFloat( 3 );
        float myRating = rs.getFloat( 4 );
        String fileLink = rs.getString( 5 );
        java.sql.Timestamp lastView = ConvertStringToTimestamp(rs.getString( 6 ));
        List<Genre> genres = getGenresInMovie(id);
        return new Movie( id,title, myRating, imdbRating, fileLink, lastView, genres );
    }

    private Timestamp ConvertStringToTimestamp(String string) {
        return java.sql.Timestamp.valueOf(string);
    }
    private String ConvertTimestampToString(java.sql.Timestamp timestamp) {
        return timestamp.toString();
    }

    /**
     * Returns a movie based on the ID
     *
     * @param movieId the id of the movie
     * @return the given movie
     * @throws SQLException
     */
    public Movie getMovieByID( int movieId ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "SELECT * FROM Movie WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, movieId );

            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                return getOneMovie( rs );
            }
        }
        return null;
    }
    /**
     * Returns a movie based on the ID
     *
     * @param title the id of the movie
     * @return the given movie
     * @throws SQLException
     */
    public Movie getMovieByName(String title ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "SELECT * FROM Movie WHERE Title = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setString( 1, title );

            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                return getOneMovie( rs );
            }
        }
        return null;
    }
    /**
     * Adds a movie to the database
     *
     * @param movie the movie you want to add
     * @return the movie added
     * @throws SQLException
     */
    public Movie addMovie( Movie movie ) throws SQLException {
        try( Connection con = dataSource.getConnection() ){
            String sql = "INSERT INTO Movie (Title, ImdbRating, MyRating, FileLink, LastView) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
            ps.setString( 1, movie.getTitle() );
            ps.setFloat( 2, movie.getImdbRating() );
            ps.setFloat( 3, movie.getMyRating() );
            ps.setString( 4, movie.getFileLink());
            ps.setString( 5, new Timestamp(System.currentTimeMillis()).toString() );

            int id = executeAndGetID( ps );

            return new Movie(id, movie);
        }
    }
    /**
     * Deletes a movie based on the id - both from the "movie" table and the
     * "MovieGenre" table
     *
     * @param movieId the id of the movie
     * @throws SQLException
     */
    public void deleteMovieByID( int movieId) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "DELETE FROM MovieGenre WHERE MovieId = ?;";

            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, movieId);
            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
            }

            String sql2 = "DELETE FROM Movie WHERE Id = ?";
            PreparedStatement ps1 = con.prepareStatement( sql2 );
            ps1.setInt( 1, movieId);
            int affectedRows1 = ps1.executeUpdate();
            if( affectedRows1 == 0 ){
                throw new SQLException( "Unable to delete movie" );
            }
        }
    }
    public void deleteGenresOnMovie( int movieId) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "DELETE FROM MovieGenre WHERE MovieId = ?;";

            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, movieId);
            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
            }
        }
    }
    /**
     * Updates a movie in the database
     *
     * @param m the movie you want to update
     * @throws SQLException
     */
    public void updateMovie( Movie m ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "UPDATE Movie SET Title = ?, ImdbRating = ?, MyRating = ?, FileLink = ?, LastView = ? WHERE Id = ?";

            PreparedStatement ps = con.prepareStatement( sql );
            ps.setString( 1, m.getTitle() );
            ps.setFloat( 2, m.getImdbRating() );
            ps.setFloat( 3, m.getMyRating() );
            ps.setString( 4, m.getFileLink() );
            ps.setString( 5, m.getLastView().toString() );
            ps.setInt( 6, m.getId() );

            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
                throw new SQLException( "Unable to update movie" );
            }
        }
    }

    /**
     * Adds a movie to a genre
     *
     * @param movie the movie you want to add
     * @param genre the genre the movie is added to
     * @throws SQLException
     */
    public void addGenreToMovie( Movie movie, Genre genre ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "INSERT INTO MovieGenre(GenreId, MovieId) VALUES (?, ?);";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, genre.getId() );
            ps.setInt( 2, movie.getId() );



            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
                throw new SQLException( "Unable to add movie to genre." );
            }
        }
    }
    /**
     * Returns the genre of a movie in a list
     *
     * @param movieId the id of the list
     * @return a list of movies linked to the same genre
     * @throws SQLException
     */
    public List<Genre> getGenresInMovie(int movieId ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "SELECT * FROM MovieGenre WHERE MovieGenre.MovieId = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, movieId );

            ResultSet rs = ps.executeQuery();

            List<Genre> genres = new ArrayList<Genre>();
            while( rs.next() ){
                genres.add(getGenreByID(rs.getInt(2)) );
            }
            return genres;
        }
    }


    /**
     * Returns a genre from the database, based on the ID
     *
     * @param searchName the id of the genre
     * @return the genre
     * @throws SQLException
     */
    public Genre getGenreByName(String searchName ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "SELECT * FROM Genre WHERE GenreName = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setString( 1, searchName );

            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                int id = rs.getInt( 1 );
                String name = rs.getString( 2 );
                return new Genre( id, name );
            }
        }
        return null;
    }
    /**
     * Returns a genre from the database, based on the ID
     *
     * @param genreId the id of the genre
     * @return the genre
     * @throws SQLException
     */
    public Genre getGenreByID(int genreId ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "SELECT * FROM Genre WHERE Id = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, genreId );

            ResultSet rs = ps.executeQuery();
            if( rs.next() ){
                int id = rs.getInt( 1 );
                String name = rs.getString( 2 );
                return new Genre( id, name );
            }
        }
        return null;
    }
    /**
     * Updates information in a genre
     *
     * @param g the genre you want to update
     * @throws SQLException
     */
    public void updateGenre( Genre g ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "UPDATE Genre SET GenreName = ? WHERE Id = ?";

            PreparedStatement ps = con.prepareStatement( sql );
            ps.setString( 1, g.getName() );
            ps.setInt( 2, g.getId() );

            int affectedRows = ps.executeUpdate();
            if( affectedRows == 0 ){
                throw new SQLException( "Unable to update genre" );
            }
        }
    }
    /**
     * Adds a genre to the database
     *
     * @param genre the genre you want to add
     * @return the genre you added
     * @throws SQLException
     */
    public Genre addGenre( Genre genre) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "INSERT INTO Genre (GenreName) VALUES (?)";
            PreparedStatement ps = con.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
            ps.setString( 1, genre.getName() );

            int id = executeAndGetID( ps );

            return new Genre( id, genre.getName() );
        }
    }
    /**
     * Deletes a genre in the database based on the ID to make sure that
     * there can be only one
     *
     * @param genreId the ID of the genre
     * @throws SQLException if the genreId does not exist
     */
    public void deleteGenreByID( int genreId) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "DELETE FROM MovieGenre WHERE GenreId = ?;";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, genreId);
            ps.executeUpdate();

            String sql1 = "DELETE FROM Genre WHERE Id = ?";
            PreparedStatement ps1 = con.prepareStatement( sql1 );
            ps1.setInt( 1, genreId );
            int affectedRows1 = ps1.executeUpdate();
            if( affectedRows1 == 0 ){
                throw new SQLException( "Unable to delete Genre" );
            }
        }
    }

    /**
     * Returns all movies in a list
     *
     * @return a list of all movies
     * @throws SQLException
     */
    public List<Genre> getAllGenres() throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery( "SELECT * FROM Genre" );

            List<Genre> genres = new ArrayList<>();
            while( rs.next() ){
                genres.add( getOneGenre( rs ) );
            }
            return genres;
        }
    }

    /**
     * @param rs the resultset from a SQL query
     * @return a genre object
     * @throws SQLException
     */
    private Genre getOneGenre(ResultSet rs) throws SQLException{
        int id = rs.getInt( 1 );
        String name = rs.getString( 2 );
        return new Genre( id,name );
    }

    /**
     * Executes a command to the sql-server and returns the generated keys
     *
     * @param ps
     * @return the key (ID) of the row created
     * @throws SQLException
     */
    private int executeAndGetID( PreparedStatement ps ) throws SQLException{
        int affectedRows = ps.executeUpdate();
        if( affectedRows == 0 ){
            throw new SQLException( "Unable to add element to database" );
        }
        ResultSet keys = ps.getGeneratedKeys();
        keys.next();
        return keys.getInt( 1 );
    }
}
