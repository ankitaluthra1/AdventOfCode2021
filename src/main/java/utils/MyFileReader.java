package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MyFileReader {

    public static List<String> getInput(String filename) {
        List<String> lines = null;
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource(filename);
            lines = Files.readAllLines(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error while reading file");
            e.printStackTrace();
        }
        return lines;
    }

}
