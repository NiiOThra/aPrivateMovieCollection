package BE;

import java.sql.Date;

public class Genre extends BaseEntity{

    private String name;

    public Genre(int id, Genre genre)
    {
        this( id, genre.getName());
    }

    /**
     * Constructor for the Movie class. This constructor is never really used,
     * since the other two cover most situations.
     *
     * @param id ID of the movie in the database.
     * @param name Name of the genre.
     */
    public Genre( int id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public Genre(String name) {
        this.setId(-1);
        this.setName(name);
    }

    public Genre() {
        this.setId(-1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * New toString method for Track, used for formatting the view in the ListView in GUI
     * @return "{Track Title} - {Track Artist}
     */
    @Override
    public String toString() {
        return this.getName();
    }

}
