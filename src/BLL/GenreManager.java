package BLL;

import BE.Genre;
import DAL.DBManager;

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

    public Genre Add(String genre) throws Exception
    {
        return dbMgr.addGenre(new Genre(genre));
    }

    public void Delete() {

    }
    public void Update() {

    }

    public void Get() {

    }
    public void GetById(int Id) {

    }
}
