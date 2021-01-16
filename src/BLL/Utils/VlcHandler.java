package BLL.Utils;

import BE.Movie;

import java.awt.*;
import java.io.File;

/**
 * The type Vlc handler.
 */
public class VlcHandler {

    /**
     * Open vlc.
     *
     * @param movie the movie
     * @throws Exception the exception
     */
    public void OpenVlc(Movie movie) throws Exception {
        File f = new File(movie.getFileLink());
        if (f.isFile() && !f.isDirectory()) {
            Desktop.getDesktop().open(f);
        } else {
            throw new Exception("Movie file not found!");
        }
    }
}