package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import lombok.Getter;

import java.io.*;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 30.06.2019.
 */
public class TrkReader {

    private String pathName;
    @Getter
    private boolean isReady;
    private BufferedReader reader;
    private static final String BEGIN_ELEMENT_OF_DATA_LINE = "T";

    public TrkReader(String pathName) {
        this.pathName = pathName;
        createBufferedReader();
    }

    public Optional<String> readLine() {
        try {
            String line = reader.readLine();
            return Optional.ofNullable(line);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public boolean isLineCorrect(String line) {
        return line.startsWith(BEGIN_ELEMENT_OF_DATA_LINE);
    }

    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createBufferedReader() {
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    new FileInputStream(
                                            new File(pathName)))));
            isReady = true;
        } catch (FileNotFoundException e) {
            isReady = false;
        }
    }
}
