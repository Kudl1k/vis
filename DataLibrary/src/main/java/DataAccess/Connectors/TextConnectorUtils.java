package DataAccess.Connectors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TextConnectorUtils {
    public static Path fullFilePath(String fileName){
        return Paths.get(GlobalConfig.dataFilesPath() + "/" + fileName);
    }

    public static Iterable<String> loadFile(Path fullPath) {
        try
        {
            if(Files.notExists(fullPath)) {
                Files.createFile(fullPath);
                return new ArrayList<String>();
            }

            return Files.readAllLines(fullPath);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<String>();

    }

    public static void saveToFile(Iterable<String> lines, String fileName) {
        try {
            Files.write(fullFilePath(fileName), lines);

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
