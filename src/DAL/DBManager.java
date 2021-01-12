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
     * Returns all songs in a list
     *
     * @return a list of all songs
     * @throws SQLException
     */
    public ArrayList<Movie> getAllMovies() throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery( "SELECT * FROM Movie" );

            ArrayList<Movie> songs = new ArrayList<>();
            while( rs.next() ){
                songs.add( getOneMovie( rs ) );
            }
            return songs;
        }
    }
    /**
     * @param rs the resultset from a SQL query
     * @return a playlist object
     * @throws SQLException
     */
    private Movie getOneMovie( ResultSet rs ) throws SQLException{
        int id = rs.getInt( 1 );
        String title = rs.getString( 2 );
        float imdbRating = rs.getFloat( 3 );
        float myRating = rs.getFloat( 4 );
        String fileLink = rs.getString( 5 );
        java.sql.Timestamp lastView = ConvertStringToTimestamp(rs.getString( 6 ));
        ArrayList<Genre> genres = getGenresInMovie(id);
        return new Movie( id,title, myRating, imdbRating, fileLink, lastView, genres );
    }

    private Timestamp ConvertStringToTimestamp(String string) {
        return java.sql.Timestamp.valueOf(string);
    }
    private String ConvertTimestampToString(java.sql.Timestamp timestamp) {
        return timestamp.toString();
    }


    /**
     * Adds a song to the database
     *
     * @param movie the song you want to add
     * @return the song added
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
            ps.setString( 5, movie.getLastView().toString() );

            int id = executeAndGetID( ps );

            return new Movie(id, movie);
        }
    }
    /**
     * Delets a song based on the id - both from the "song" table and the
     * "playlistsong" table
     *
     * @param movieId the id of the song
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
    /**
     * Updates a song in the database
     *
     * @param m the song you want to update
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
     * Adds a song to a playlist
     *
     * @param song the song you want to add
     * @param playlist the playlist the song is added to
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
                throw new SQLException( "Unable to add song to playlist." );
            }
        }
    }
    /**
     * Returns the songs of a playlist in a list
     *
     * @param movieId the id of the list
     * @return a list of songs linked to the same playlist
     * @throws SQLException
     */
    public ArrayList<Genre> getGenresInMovie(int movieId ) throws SQLException{
        try( Connection con = dataSource.getConnection() ){
            String sql = "SELECT * FROM MovieGenre WHERE MovieGenre.MovieId = ?";
            PreparedStatement ps = con.prepareStatement( sql );
            ps.setInt( 1, movieId );

            ResultSet rs = ps.executeQuery();

            ArrayList<Genre> genres = new ArrayList<Genre>();
            while( rs.next() ){
                genres.add(getGenreByID(rs.getInt(2)) );
            }
            return genres;
        }
    }
    /**
     * Returns a song based on the ID
     *
     * @param movieId the id of the song
     * @return the given song
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
     * Returns a playlist from the database, based on the ID
     *
     * @param genreId the id of the playlist
     * @return the Playlist
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
     * Updates information in a playlist
     *
     * @param g the playlist you want to update
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
     * Adds a playlist to the database
     *
     * @param genre the playlist you want to add
     * @return the playlist you added
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
     * Deletes a playlist in the database based on the ID to make sure that
     * there can be only one
     *
     * @param genreId the ID of the playlist
     * @throws SQLException if the playlistID does not exist
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
