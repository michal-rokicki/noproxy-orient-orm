package pl.megaoak.orientorm.util;

import com.orientechnologies.orient.core.record.impl.ODocument;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Resources {
    static public String fileToString(String path) {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(path);
            return new String(Files.readAllBytes(Paths.get(url.toURI())));
        } catch (Exception e) {
            throw new RuntimeException("Can't read file: "+path, e);
        }
    }

    static public ODocument documentFromFile(String path) {
        ODocument result = new ODocument();
        result.fromJSON(fileToString(path));
        return result;
    }
}
