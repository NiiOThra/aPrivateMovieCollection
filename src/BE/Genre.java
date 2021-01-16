package BE;


/**
 * The type Genre.
 */
public class Genre extends BaseEntity{

    private String name;

    /**
     * Instantiates a new Genre.
     *
     * @param id    the id
     * @param genre the genre
     */
    public Genre(int id, Genre genre)
    {
        this( id, genre.getName());
    }

    /**
     * Constructor for the Movie class. This constructor is never really used,
     * since the other two cover most situations.
     *
     * @param id   ID of the movie in the database.
     * @param name Name of the genre.
     */
    public Genre( int id, String name) {
        this.setId(id);
        this.setName(name);
    }

    /**
     * Instantiates a new Genre.
     *
     * @param name the name
     */
    public Genre(String name) {
        this.setId(-1);
        this.setName(name);
    }

    /**
     * Instantiates a new Genre.
     */
    public Genre() {
        this.setId(-1);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * New toString method for Movie, used for formatting the view in the ListView in GUI
     * @return "{Movie Title}
     */
    @Override
    public String toString() {
        return this.getName();
    }

}
