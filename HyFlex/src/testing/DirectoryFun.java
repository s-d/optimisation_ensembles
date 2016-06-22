package testing;

import java.io.File;

/**
 * Created by Sam on 22/06/2016.
 */
public class DirectoryFun {
    public static void main(String[] args) {
        File dir = new File("path/dir");

        if (!dir.exists()) {
            System.out.println("Creating directory " + dir.getName());
            dir.mkdirs();
        }
    }
}
