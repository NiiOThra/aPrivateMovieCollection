package BLL;

import DAL.DBManager;

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
    public void Add() {

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
