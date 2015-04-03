package theGUI;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.*;





public class mp3Filter extends FileFilter {

    //Accept only mp3 file format
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.mp3) ) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Just MP3s";
    }
}


