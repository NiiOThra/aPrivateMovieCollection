package BLL.Utils;

import BE.Movie;

import java.awt.*;
import java.io.File;

public class VlcHandler {

    public void OpenVlc(Movie movie) throws Exception {
        File f = new File(movie.getFileLink());
        if (f.isFile() && !f.isDirectory()) {
            Desktop.getDesktop().open(f);
        } else {
            throw new Exception("Movie file not found!");
        }
    }
}