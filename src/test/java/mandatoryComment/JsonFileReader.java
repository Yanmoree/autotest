package mandatoryComment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileReader {

    public static String readJsonFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }

    public static String getJsonFromResources(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/test/java/mandatoryComment/JSON/" + fileName)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON from resources: " + fileName, e);
        }
    }
}