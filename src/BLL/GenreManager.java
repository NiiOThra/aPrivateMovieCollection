package BLL;

public class GenreManager {

    private static GenreManager instance = null;

    public static GenreManager getInstance() {
        if( instance == null ){
            return new GenreManager();
        }else{
            return instance;
        }
    }
}
