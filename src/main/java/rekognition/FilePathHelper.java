package rekognition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilePathHelper {
    public static List<String> getImagesFilePaths(String directory) {
        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        List<String> files = new ArrayList<>();
        if (directoryListing == null) {
            return files;
        }
        for (File child : directoryListing) {
            files.add(child.toString());
        }
        return files;
    }
}
