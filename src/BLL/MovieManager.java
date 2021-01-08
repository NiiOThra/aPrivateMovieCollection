package BLL;

public class MovieManager {

    private static MovieManager instance = null;

    public static MovieManager getInstance() {
        if( instance == null ){
            return new MovieManager();
        }else{
            return instance;
        }
    }
}
