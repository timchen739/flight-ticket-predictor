package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class CsvFileHelper {
    public static void createFile(String fileName, Iterator<String[]> iterator) throws IOException {
        FileWriter writer = new FileWriter(String.format("src/main/resources/%s", fileName));

        String[] headers = {"Route", "Search Date", "Flight Dates", "Current Price", "Lowest Price"};
        String[] row1 = {"John Doe", "25", "New York"};

        for(int i=0; i < headers.length; i++) {
            String header = headers[i];
            writer.append(header);
            if(i < headers.length-1) {
                writer.append(",");
            }
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
