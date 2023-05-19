package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class CsvFileHelper {

    public static void createFile(String fileName, Iterator<String[]> iterator, Iterator<String> headers) throws IOException {
        FileWriter writer = new FileWriter(String.format("src/main/resources/%s", fileName));

        while(headers.hasNext()) {
            String header = headers.next();
            writer.append(header);
            writer.append(",");
        }

        writer.append("\n");

        while(iterator.hasNext()) {
            writer.append(String.join(",", iterator.next()));
            writer.append("\n");
        }

        writer.flush();
        writer.close();
    }
}
