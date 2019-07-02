package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.io;

import lombok.Getter;

import java.io.*;

/**
 * Created by Jakub Filipiak on 01.07.2019.
 */
public class TxtWriter {

    private File txtFile;
    @Getter
    private boolean isReady;
    private FileWriter writer;
    private static final String NEW_LINE = System.getProperty("line.separator");
    private boolean isFirstLineWritten = false;

    public TxtWriter(File txtFile) {
        this.txtFile = txtFile;
        createFileWriter();
    }

    public void writeLine(String text) {
        insertNewLine();
        try {
            writer.write(text);
            isFirstLineWritten = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createFileWriter() {
        try {
            writer = new FileWriter(txtFile);
            isReady = true;
        } catch (IOException e) {
            isReady = false;
        }
    }

    private void insertNewLine() {
        if (isFirstLineWritten) {
            try {
                writer.write(NEW_LINE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
