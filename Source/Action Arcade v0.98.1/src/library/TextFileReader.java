package library;

import data.Loader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class TextFileReader {

    // Buffer textoveho dokumenutu
    private ArrayList<String> buffer = new ArrayList<String>();
    // Vstup
    BufferedReader input;

    /*
     * Konstruktor
     */
    public TextFileReader(String path) {
        InputStream in = Loader.getInputStream(path);
        input = new BufferedReader(new InputStreamReader(in));
        try {
            String s;
            while ((s = input.readLine()) != null) {
                if (!s.contains("#") && !s.isEmpty()) {
                    buffer.add(s);
                }
            }
        } catch (IOException ex) {
        }
    }

    /*
     * Iterator
     */
    public Iterator getIterator() {
        return buffer.iterator();
    }
}
