package DAL;

import BE.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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

}
