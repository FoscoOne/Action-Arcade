package data;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class Loader {

    public static URL getURL(String ref) {
        return ClassLoader.getSystemResource(ref);
    }

    public static InputStream getInputStream(String ref) {
        return ClassLoader.getSystemResourceAsStream(ref);
    }

    public static File getFile(String ref) {
        return new File(ClassLoader.getSystemResource(ref).getPath());
    }
}
